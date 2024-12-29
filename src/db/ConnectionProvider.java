package db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionProvider {
    private static Connection connection;

    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_crud_operation", "root", "password");
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
