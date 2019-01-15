package base.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private String url;
    private String username;
    private String password;


    public ConnectionUtil() {};

    public ConnectionUtil(String url, String username, String password, Driver driver) throws SQLException {

        DriverManager.registerDriver(driver);

        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.username, this.password);
    }

}

