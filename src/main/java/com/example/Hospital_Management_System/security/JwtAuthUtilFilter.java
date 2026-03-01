package com.example.Hospital_Management_System.security;

import com.example.Hospital_Management_System.entities.User;
import com.example.Hospital_Management_System.repo.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthUtilFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Incoming Request" + request.getRequestURI());

            final String requestTokenHeader = request.getHeader("Authorization");//getting header from incoming token
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {//conditional check if Header start with Bearer or not If it is not start with Bearer filter chain move forward in filter
                filterChain.doFilter(request, response);
                return;
            }
            //Token saved in the form of array
            String token = requestTokenHeader.split("Bearer ")[1];//splitting and taking first index token
            String username = authUtil.getUsernameFromToken(token);//getting username from token
            //This is for setting our own security context authentication on each request
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username).orElseThrow();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request, response);//after adding own filter then move forward in filter chain.
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
    }
}
