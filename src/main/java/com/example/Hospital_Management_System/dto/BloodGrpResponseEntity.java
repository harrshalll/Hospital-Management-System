package com.example.Hospital_Management_System.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BloodGrpResponseEntity {
    private String bloodGrp;
    private Long count;
}
