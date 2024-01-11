package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
/**
 * Hello world!
 *
 */
public class Conn
{
    private static HikariDataSource HDS;

    static {
        HikariConfig HC = new HikariConfig();
        HC.setDriverClassName("com.mysql.cj.jdbc.Driver");
        HC.setJdbcUrl("jdbc:mysql://localhost:3322/userNP");
        HC.setUsername("root");
        HC.setPassword("Modernwarfare459");

        HC.setMaximumPoolSize(10);
        HC.setMinimumIdle(3);
        HC.setIdleTimeout(10*60000);

        HDS = new HikariDataSource(HC);
    }

    public static HikariDataSource getHDS() {
        return HDS;
    }
}
