package com.example.Hospital_Management_System.security;

import com.example.Hospital_Management_System.dto.LoginRequestDto;
import com.example.Hospital_Management_System.dto.LoginResponseDto;
import com.example.Hospital_Management_System.dto.SignUpRequestDto;
import com.example.Hospital_Management_System.dto.SignUpResponseDto;
import com.example.Hospital_Management_System.entities.Patient;
import com.example.Hospital_Management_System.entities.User;
import com.example.Hospital_Management_System.entities.type.AuthProviderType;
import com.example.Hospital_Management_System.entities.type.RoleType;
import com.example.Hospital_Management_System.repo.PatientRepo;
import com.example.Hospital_Management_System.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
//@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PatientRepo patientRepo;
    // Manually define the constructor to apply @Lazy to only one field
    public AuthService(@Lazy AuthenticationManager authenticationManager,
                       AuthUtil authUtil,
                       UserRepository userRepository,PatientRepo patientRepo) {
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
        this.userRepository = userRepository;
        this.patientRepo = patientRepo;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.getAccessToken(user);
        return new LoginResponseDto(token,user.getId());
    }
    @Transactional
    public User signUpInternal(SignUpRequestDto signupRequestDto, AuthProviderType authProviderType, String providerId) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if (user != null) throw new IllegalArgumentException("User already exists");

        user = User.builder()
                .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .authProviderType(authProviderType)
                .roles(signupRequestDto.getRoles()) // Role.PATIENTIf any user signup. first save him as a patient
                .build();

        if (authProviderType == AuthProviderType.EMAIL) {
            user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDto.getPassword()));
        }
        user = userRepository.save(user);
        Patient patient = Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(user)
                .build();
//        patientRepo.save(patient);
        return user;
    }
    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto){
        User user = signUpInternal(signUpRequestDto, AuthProviderType.EMAIL, null);
//        if(userRepository.existsByUsername(signUpRequestDto.getUsername())) {
//            throw new IllegalArgumentException("User Already Exists");
//        }
//
////        if(user1!=null) throw new IllegalArgumentException("User Already Exists");
//        User user1 = userRepository.save(User.builder()
//                .username(signUpRequestDto.getUsername())
//                .password(new BCryptPasswordEncoder().encode(signUpRequestDto.getPassword()))
//                .build()
//        );
        return new SignUpResponseDto(user.getId(), user.getUsername());
    }

    public void handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        AuthProviderType authProviderType = authUtil.getAuthProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId);
        User user = userRepository.findByProviderIdAndAuthProviderType(providerId,authProviderType).orElse(null);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser == null) {
            // signup flow:
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signUpInternal(new SignUpRequestDto(username,null,name,Set.of(RoleType.PATIENT)),authProviderType,providerId);
        } else if(user != null) {
            //login
            if(email != null && !email.isBlank() && !email.equals(user.getUsername())) {
                user.setUsername(email);
                userRepository.save(user);
            }
        } else {
            throw new BadCredentialsException("This email is already registered with provider "+emailUser.getAuthProviderType());
        }
        //fetch providerType and providerId
        //save providerType and providerId info with user
        //if user already present : directly login
        //otherwise first signup then login
    }
}
