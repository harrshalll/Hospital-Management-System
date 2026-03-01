package com.example.Hospital_Management_System.repo;

import com.example.Hospital_Management_System.dto.BloodGrpResponseEntity;
import com.example.Hospital_Management_System.entities.Patient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
    Patient findByName(String name);

    //List<Patient> findByBloodGrp(String bloodGrp);

    List<Patient> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    //JPQL
    @Query("select p from Patient p where p.birthDate  > :birthDate")
    List<Patient> findBornAfterDate(@Param("birthDate")LocalDate birthDate);

    //Projection In JPQL
//    @Query("select new com.example.Hospital_Management_System.dto.BloodGrpResponseEntity(p.bloodGrp, " +"Count(p)) from Patient p group by p.bloodGrp")
//    List<BloodGrpResponseEntity> countEachBloodGrpType();

    //Native Query Language
    @Query(value = "select * from patient_table", nativeQuery = true)
    Page<Patient> findByAll(Pageable pageable);
}
