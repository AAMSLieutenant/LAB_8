/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oracle;


import interfaces.DaoFactoriable;
import interfaces.EmployeeDaoble;
import interfaces.OfficeDaoble;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;


/**
 *
 * @author Rostislav Stakhov
 */
public class OracleDaoFactory implements DaoFactoriable{

    private String user = "admin";//Логин пользователя
    private String password = "admin";//Пароль пользователя
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";//URL адрес
    private String driver = "oracle.jdbc.driver.OracleDriver";//Имя драйвера


    public OracleDaoFactory()
    {
        try
        {
            System.out.println("-------- Oracle JDBC Connection Testing ------");
            Class.forName(driver);//Регистрируем драйвер
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
        }
        System.out.println("Oracle JDBC Driver Registered!");
//        Connection connection;
//
//        try {
//
//            connection = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@localhost:1521:xe", "manager", "manager");
//
//        } catch (SQLException e) {
//
//            System.out.println("Connection Failed! Check output console");
//            e.printStackTrace();
//            return;
//
//        }
//
//        if (connection != null) {
//            System.out.println("You made it, take control your database now!");
//        } else {
//            System.out.println("Failed to make connection!");
//        }

    }




    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, user, password);
        //return null;
    }


    public EmployeeDaoble getEmployeeDao() throws Exception
    {
        return new EmployeeDao(DriverManager.getConnection(url, user, password));
        //return null;
    }


    public OfficeDaoble getOfficeDao() throws Exception
    {
        return new OfficeDao(DriverManager.getConnection(url, user, password));

    }

}
