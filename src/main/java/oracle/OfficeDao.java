/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oracle;

import interfaces.OfficeDaoble;
import java.util.List;
import logic.Office;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Rostislav Stakhov
 */
public class OfficeDao implements OfficeDaoble{

    /** Объект для управления персистентным состоянием объекта Office */


    private final Connection connection;
    private String role;

    private static final org.apache.log4j.Logger log= Logger.getLogger(OfficeDao.class);
    static {
        PropertyConfigurator.configure("log4j.properties");
    }




    public OfficeDao(Connection connection)
    {
        this.connection = connection;
    }

    /** Создает новую запись и соответствующий ей объект */
    public void create(Office office) throws Exception{


        if((consider(getRole(), 'w', 16))!=-1){
            log.info("OfficeDao create()");
            int count=0;
            String statement="SELECT COUNT(*) FROM Objects";
            PreparedStatement ps=connection.prepareStatement(statement);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                count=rs.getInt(1);
            }
            System.out.println("Number of objects: "+count);
            count++;


            ps=connection.prepareStatement("INSERT INTO Objects (object_id, object_name, type_id) "
                    + "VALUES ((select to_number('3" + count + "' || (to_char(sysdate, 'DDMMYYHH24MI')))from dual), ?, ?) ");

            ps.setString(1, office.getObjName());
            ps.setInt(2, 16);
            //ps.setObject(4, null);
            ps.executeUpdate();


            ps=connection.prepareStatement("INSERT INTO Parameters (attr_id, object_id, number_info) "
                    +"VALUES (26, (SELECT object_id FROM objects WHERE object_name=?), ?)");
            ps.setString(1, office.getObjName());
            ps.setInt(2, office.getEmpCount());
            ps.executeUpdate();


            ps=connection.prepareStatement("INSERT INTO Parameters (attr_id, object_id, text_info) "
                    +"VALUES (27, (SELECT object_id FROM objects WHERE object_name=?), ?)");
            ps.setString(1, office.getObjName());
            ps.setString(2, office.getLocation());
            ps.executeUpdate();

            connection.commit();
            try{
                ps.close();
            }
            catch(SQLException e){
                log.error("OfficeDao create(): SQLException");
                e.printStackTrace();

            }

        }

    }

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public Office read(long key) throws Exception{


        if((consider(getRole(), 'r', 16))==-1){
            log.error("OfficeDao read() access error");
            return null;
        }
        log.info("OfficeDao read()");
        String statement="select o.object_id, o.object_name, p.text_info, p.number_info, a.attr_id"
                +" from objects o"
                +" inner join attributes a on a.TYPE_ID=16"
                +" inner join parameters p on p.object_id = o.object_id"
                +" and a.ATTR_ID=p.ATTR_ID"
                +" and o.OBJECT_ID=?";

        PreparedStatement ps=connection.prepareStatement(statement);
        ps.setLong(1, key);
        ResultSet rs=ps.executeQuery();
        Office o=new Office();
        while(rs.next()){

            o.setObjId(rs.getLong("object_id"));

            o.setObjName(rs.getString("object_name"));
            //System.out.println("NAME "+o.getObjName());
            if(rs.getInt("attr_id")==26) o.setEmpCount(rs.getInt("number_info"));

            if(rs.getInt("attr_id")==27) o.setLocation(rs.getString("text_info"));

//            System.out.println("id:"+o.getObjId()+"\n"+"Шифр:"+o.getObjName()+"\n"+"Город:"+o.getLocation()+"\n"+"Сотрудники:"+o.getEmpCount());
            System.out.println(o);
        }
        System.out.println("------------------------------");
        try{
            ps.close();
        }
        catch(SQLException e){
            log.error("OfficeDao read(): SQLException");
        }
        return o;

    }

    /** Сохраняет состояние объекта group в базе данных */
    public void update(long key, Office office) throws Exception{


        if(consider(getRole(), 'w', 16)!=-1)
        {
            log.info("OfficeDao update()");
            boolean flag=false;
            String statement="SELECT object_id FROM Objects";
            PreparedStatement ps=connection.prepareStatement(statement);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(rs.getLong(1)==key){
                    flag=true;
                    log.info("OfficeDao update() MATCH: "+rs.getLong(1));
                    break;
                }

            }

            if(flag==true){
                log.info("OfficeDao update() started");
                log.info(office.getObjName());
                statement="UPDATE objects"
                        + " SET object_name=?"
                        + " WHERE object_id=?";


                ps=connection.prepareStatement(statement);

                ps.setString(1, office.getObjName());

                ps.setLong(2, key);


                ps.executeUpdate();

                connection.commit();

                statement="UPDATE parameters"
                        + " SET number_info=?"
                        + " WHERE object_id=?"
                        + " AND attr_id=26";
                ps=connection.prepareStatement(statement);
                ps.setInt(1, office.getEmpCount());
                ps.setLong(2, key);
                ps.executeUpdate();
                connection.commit();

                statement="UPDATE parameters"
                        + " SET text_info=?"
                        + " WHERE object_id=?"
                        + " AND attr_id=27";
                ps=connection.prepareStatement(statement);
                ps.setString(1, office.getLocation());
                ps.setLong(2, key);
                ps.executeUpdate();
                connection.commit();


            }
            else{
                log.error("OfficeDao update() error: wrong object ID");
//                System.out.println("WRONG ID");
            }

            try{
                ps.close();
            }
            catch(SQLException e){
                log.error("OfficeDao update(): SQLException");
                e.printStackTrace();
            }

        }
    }

    /** Удаляет запись об объекте из базы данных */
    public void delete(long key) throws Exception{

        System.out.println(consider(getRole(), 'w', 16));
        if(consider(getRole(), 'w', 16)==1)
        {
            log.info("OfficeDao delete()");
            boolean flag=false;
            String statement="SELECT object_id FROM Objects";
            PreparedStatement ps=connection.prepareStatement(statement);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(rs.getLong(1)==key){
                    flag=true;
                    System.out.println("MATCH: "+rs.getLong(1));
                    break;
                }
            }

            if(flag==true){


                ps=connection.prepareStatement("DELETE FROM Parameters WHERE object_id=?");
                ps.setLong(1, key);
                ps.executeUpdate();


                ps=connection.prepareStatement("DELETE FROM Objects WHERE object_id=?");
                ps.setLong(1, key);
                ps.executeUpdate();


                connection.commit();

            }
            else{
                log.error("EmployeeDao delete():wrong object ID");
            }


            try{
                ps.close();
            }
            catch(SQLException e){
                e.printStackTrace();
                log.error("OfficeDao delete(): SQLException");
            }


        }

    }

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<Office> getAll() throws Exception{

        log.info("OfficeDao getAll()");

        List<Office> objects=new ArrayList<Office>();
        List<Long> arr=new ArrayList<Long>();

        //System.out.println(consider(getRole(), 'r', 16));

        if((consider(getRole(), 'r', 16))==-1){
            log.error("OfficeDao getAll() access error");
            return null;
        }
        log.error("OfficeDao getAll(): SQLException");

        String statement="SELECT object_id FROM Objects where type_id=16";

        PreparedStatement ps=connection.prepareStatement(statement);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){

            arr.add(rs.getLong("object_id"));
        }



        for(int i=0;i<arr.size();i++){
            objects.add(read(arr.get(i)));
        }

        return objects;
    }

    public void quit(){

        log.info("OfficeDao closing connection");
        try
        {
            this.connection.close();
        }
        catch(Exception e)
        {
            log.error("OfficeDao quit(): SQLException");
            e.printStackTrace();

        }


    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){

        this.role=role;
    }

    public int consider(String role, char mode, int type){

        log.info("OfficeDao concider()");
        log.info("Working in "+role.toUpperCase()+" mode:");
        String check=new String();
        PreparedStatement ps=null;
        ResultSet rs=null;
        String column=new String();
        int result=0;

        if(role.equals("admin")){

            if(mode=='r'){
                column="AREAD";
            }
            if(mode=='w'){
                column="AWRITE";
            }
        }

        if(role.equals("manager")){

            if(mode=='r'){
                column="MREAD";
            }
            if(mode=='w'){
                column="MWRITE";
            }
        }

        if(role.equals("worker")){

            if(mode=='r'){
                column="WREAD";
            }
            if(mode=='w'){
                column="WWRITE";
            }
        }
        log.info("OfficeDao concider(): current role: "+column);

        try{

            check="SELECT "+column+" FROM types WHERE type_id=16";
            ps=connection.prepareStatement(check);
            System.out.println(check);
            rs=ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt(column));
                result=rs.getInt(column);
            }

            log.info("OfficeDao Office type: "+result);
            if(result==0){
                log.info("OfficeDao Office type: ACCESS UNCONFIGURED");
                check="SELECT "+column+" FROM types WHERE type_id=14";
                ps=connection.prepareStatement(check);
                System.out.println(check);
                rs=ps.executeQuery();
                while(rs.next()){
                    //System.out.println(rs.getInt(column));
                    result=rs.getInt(column);
                }
                log.info("OfficeDao Location type: "+result);
            }

            if(result==0){
                log.info("OfficeDao Location type: ACCESS UNCONFIGURED");
                check="SELECT "+column+" FROM types WHERE type_id=11";
                ps=connection.prepareStatement(check);
                rs=ps.executeQuery();
                while(rs.next()){
//                    System.out.println("COLLUUUMMMNNN"+rs.getInt(column));
                    result=rs.getInt(column);
                }
                log.info("OfficeDao Object type: "+result);
            }
            if(result==0){
                log.info("CORE Office/object ACCESS DENIED");
            }

        }
        catch(SQLException e){
            log.error("OfficeDao concider(): SQLException");
            e.printStackTrace();
        }


        if(result==1){
            log.info("OfficeDao access type: APPROWED");

        }

        if(result==-1){
            log.info("OfficeDao access type: DENIED");
        }

        return result;
    }


}
