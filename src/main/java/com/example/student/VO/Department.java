package com.example.student.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private long Id;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}
