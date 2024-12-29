package dao;

import db.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Users;

public class UserDao {

    public void createTable(){
        try{
            Connection connection = ConnectionProvider.getConnection();
            String sql = "CREATE TABLE IF NOT EXISTS USERS (id int AUTO_INCREMENT PRIMARY KEY, name varchar(50), age int)";
            Statement statement = connection.createStatement();
            boolean execute = statement.execute(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertData(Users user){
        try{
            Connection connection = ConnectionProvider.getConnection();
            String sql = "INSERT INTO USERS (name, age) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.execute();
            System.out.println("User inserted: " + user.getName());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Users> fetchData(){
        List<Users> users = new ArrayList<>();
        try{
            Connection connection = ConnectionProvider.getConnection();
            String sql = "SELECT * FROM USERS";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                users.add(user);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public Users fetchUser(int id) {
        try {
            Connection connection = ConnectionProvider.getConnection();
            String sql = "SELECT * FROM USERS WHERE ID =" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                return user;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
