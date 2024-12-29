import dao.UserDao;
import db.ConnectionProvider;
import file.FileOperations;
import models.Users;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        UserDao userDao = new UserDao();
        FileOperations fileOperations = new FileOperations();

        //1. Create table
        userDao.createTable();

//        //2. Insert data into users;
//        Users user = new Users();
//        user.setName("Santosh");
//        user.setAge(30);
//        userDao.insertData(user);

        //3. Fetch data
        List<Users> users = userDao.fetchData();
        System.out.println("ID" + " Name" + " AGE");
        for (Users user:users){
            System.out.print(user.getId());
            System.out.print(user.getName());
            System.out.println(user.getAge());
        }

//        //4. Write data to excel
//        fileOperations.writeDataToExcel(users);

//        //5. Fetch data from excel and storing in the DB
//        fileOperations.readDataFromExcel("Users.xlsx");

//        //6. Write data to XML
//        fileOperations.writeDataToXML(users);

        //7. Read data from XML
        fileOperations.readDataFromXML();


    }
}