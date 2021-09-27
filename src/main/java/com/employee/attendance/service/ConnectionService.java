package com.employee.attendance.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConnectionService {
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:harperdb:Server=https://xxx.harperdbcloud.com;User=xxx;Password=xxx;UseSSL=true;");
    }
}
