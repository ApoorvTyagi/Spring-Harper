package com.employee.attendance.controller;

import com.employee.attendance.dto.EmployeeDataDTO;
import com.employee.attendance.dto.EmployeeEditDataDTO;
import com.employee.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class AttendanceController {

    @Autowired
    AttendanceService service;

    @GetMapping("/")
    public String healthCheck(){
        return "OK";
    }

    @GetMapping(value = "/api/get/all/leaves/{employeeId}")
    public List<HashMap<String, String>> getAllLeavesForEmployee(@PathVariable String employeeId) {
        return service.getAllLeavesForEmployee(employeeId);
    }

    @PostMapping(value = "/api/add/leave")
    public HashMap<String, String> addNewLeave(@RequestBody EmployeeDataDTO employeeData) {
        return service.addNewLeaveForEmployee(employeeData);
    }

    @PutMapping(value = "/api/edit/leave")
    public HashMap<String, String> editLeave(@RequestBody EmployeeEditDataDTO employeeEditData) {
        return service.editLeaveForEmployee(employeeEditData);
    }

    @DeleteMapping(value = "/api/cancel/leave")
    public HashMap<String, String> cancelLeave(@RequestBody EmployeeDataDTO employeeData) {
        return service.cancelLeaveForEmployee(employeeData);
    }
}
