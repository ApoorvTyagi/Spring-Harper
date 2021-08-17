package com.employee.attendance.dto;

import lombok.Data;

@Data
public class EmployeeEditDataDTO {
    private String employeeId;
    private String previousDate;
    private String newDate;
}
