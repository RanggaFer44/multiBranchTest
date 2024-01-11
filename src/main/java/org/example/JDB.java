package org.example;

import  org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDB {

    void testDriver () {

        try {

            Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);

        } catch (SQLException exception) {
            // TODO: handle exception
            Assertions.fail(exception);
        }

    }

}
