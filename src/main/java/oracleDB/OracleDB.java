/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleDB;


import oracle.*;
import logic.Office;
import logic.Employee;
import interfaces.*;


import java.util.*;
import java.sql.*;
import java.math.BigInteger;



import java.util.Locale;
import java.sql.Date;





public class OracleDB {

    public static final String ADMIN_ROLE="admin";//Администратор - можно все
    public static final String MANAGER_ROLE="manager";//Менеджер - можно считывать все и перезаписывать почти все
    public static final String WORKER_ROLE="worker";//Рядовой служащий - возможно только чтение

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Locale.setDefault(Locale.US);
        try{
            //System.out.println(Long.MAX_VALUE);

            OracleDaoFactory df=new OracleDaoFactory();//Получение объекта связи с БД

//            OfficeDaoble of=df.getOfficeDao();//Получение объекта для работы с персистентным состоянием Office
            //of.setRole(ADMIN_ROLE);
//            of.setRole(MANAGER_ROLE);
            //of.setRole(WORKER_ROLE);

            //of.create(new Office("NetBeans", 66, "NetBeansLocation"));
//            of.read(31);
            //of.update(id, new Office("ВАРТРУКК", 100, "KisusyIEV"));
//            of.delete(31);
//            of.getAll();
            //of.quit();




//            EmployeeDaoble em=df.getEmployeeDao();//Получение объекта для работы с персистентным состоянием Office
            //em.setRole(ADMIN_ROLE);
//            em.setRole(MANAGER_ROLE);
            //em.setRole(WORKER_ROLE);

            //em.create(new Employee("MARK", 13, "Markus", "Developer", "12.12.2013", 332222d, null));

            //em.read(id);
            //em.getAll();
            //em.update(id, new Employee("Maks", 1111, "Maksim", "Manager", "11.11.2001", 5555d, null));
//            em.delete(33);
            //of.quit();

        }
        catch(Exception e){

        }

    }

}
