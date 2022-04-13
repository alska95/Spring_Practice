package com.example.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.example.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection = {}, class = {}" ,connection , connection.getClass() );
            return connection;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
}
