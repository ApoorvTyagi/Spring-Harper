package com.employee.attendance.service;

import com.employee.attendance.dto.EmployeeDataDTO;
import com.employee.attendance.dto.EmployeeEditDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class AttendanceService {

    @Autowired
    ConnectionService connectionService;

    public List<HashMap<String, String>> getAllLeavesForEmployee(String empId) {
        log.info("Getting all leaves for employee - {}",empId);
        List<HashMap<String, String>> resultList = new ArrayList<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("Select * From Employee_Leaves.Leaves where empId = ?");
            statement.setString(1,empId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                HashMap<String, String> result = new HashMap<>();
                result.put("date_of_apply",new Date(Long.parseLong(resultSet.getString("__createdtime__"))).toString());
                result.put("last_update_date",new Date(Long.parseLong(resultSet.getString("__updatedtime__"))).toString());
                result.put("leave_applied_for",resultSet.getString("date"));
                result.put("employee_id",resultSet.getString("empId"));
                resultList.add(result);
            }
            conn.close();
        } catch (Exception e){
            log.error("Error occurred", e);
            HashMap<String, String> result = new HashMap<>();
            result.put("Error",e.getMessage());
            resultList.add(result);
        }
        return resultList;
    }

    public HashMap<String, String> addNewLeaveForEmployee(EmployeeDataDTO employeeData) {
        log.info("Inserting new leave for employee - {}",employeeData.getEmployeeId());
        HashMap<String, String> result = new HashMap<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Employee_Leaves.Leaves (date, empId) VALUES (?,?)");
            statement.setString(1, employeeData.getDate());
            statement.setString(2, employeeData.getEmployeeId());
            int count = statement.executeUpdate();
            if(count>0) {
                result.put("Message", "Success");
                result.put("Affected rows", String.valueOf(count));
            }
            conn.close();
        } catch (Exception e){
            log.error("Error occurred", e);
            result.put("Error",e.getMessage());
        }
        return result;
    }

    public HashMap<String, String> editLeaveForEmployee(EmployeeEditDataDTO employeeEditData) {
        log.info("Updating leave for employee - {}",employeeEditData.getEmployeeId());
        HashMap<String, String> result = new HashMap<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE Employee_Leaves.Leaves SET date = ? WHERE empId=? and date = ?");
            statement.setString(1, employeeEditData.getNewDate());
            statement.setString(2, employeeEditData.getEmployeeId());
            statement.setString(3, employeeEditData.getPreviousDate());
            int count = statement.executeUpdate();
            if(count>0) {
                result.put("Message", "Success");
                result.put("Affected rows", String.valueOf(count));
            }
            conn.close();
        } catch (Exception e){
            log.error("Error occurred", e);
            result.put("Error",e.getMessage());
        }
        return result;
    }

    public HashMap<String, String> cancelLeaveForEmployee(EmployeeDataDTO employeeData) {
        log.info("Cancelling leave for employee - {}",employeeData.getEmployeeId());
        HashMap<String, String> result = new HashMap<>();
        try {
            Connection conn = connectionService.createConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Employee_Leaves.Leaves WHERE empId = ? and date = ?");
            statement.setString(1, employeeData.getEmployeeId());
            statement.setString(2, employeeData.getDate());
            int count = statement.executeUpdate();
            if(count>0) {
                result.put("Message", "Success");
                result.put("Affected rows", String.valueOf(count));
            }
            conn.close();
        } catch (Exception e){
            log.error("Error occurred", e);
            result.put("Error",e.getMessage());
        }
        return result;
    }
}
