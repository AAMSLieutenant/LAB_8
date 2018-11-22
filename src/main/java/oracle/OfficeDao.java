/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oracle;

import interfaces.OfficeDaoble;
import java.util.List;
import logic.Office;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Rostislav Stakhov
 */
public class OfficeDao implements OfficeDaoble{

    /** Объект для управления персистентным состоянием объекта Office */


    private final Connection connection;
    private String role;





    public OfficeDao(Connection connection)
    {

        this.connection = connection;
    }

    /** Создает новую запись и соответствующий ей объект */
    public void create(Office office) throws Exception{

        if((consider(getRole(), 'w', 16))!=-1){


            System.out.println("Office create()");

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
            try{ps.close();}catch(SQLException e){}

        }

    }

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public Office read(long key) throws Exception{

        if((consider(getRole(), 'r', 16))==-1){
            return null;
        }


        System.out.println("Office read()");



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

            System.out.println("READY");

            o.setObjId(rs.getLong("object_id"));

            o.setObjName(rs.getString("object_name"));
            //System.out.println("NAME "+o.getObjName());
            if(rs.getInt("attr_id")==26) o.setEmpCount(rs.getInt("number_info"));

            if(rs.getInt("attr_id")==27) o.setLocation(rs.getString("text_info"));

            System.out.println("id:"+o.getObjId()+"\n"+"Шифр:"+o.getObjName()+"\n"+"Город:"+o.getLocation()+"\n"+"Сотрудники:"+o.getEmpCount());

        }
        System.out.println("------------------------------");
        try{ps.close();}catch(SQLException e){}
        return o;

    }

    /** Сохраняет состояние объекта group в базе данных */
    public void update(long key, Office office) throws Exception{

        if(consider(getRole(), 'w', 16)!=-1)
        {
            System.out.println("Office update()");
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
                System.out.println("Update started");
                System.out.println(office.getObjName());
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
                System.out.println("WRONG ID");
            }

            try{ps.close();}catch(SQLException e){}

        }
    }

    /** Удаляет запись об объекте из базы данных */
    public void delete(long key) throws Exception{

        System.out.println(consider(getRole(), 'w', 16));
        if(consider(getRole(), 'w', 16)==1)
        {

            System.out.println("Office delete()");
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
                System.out.println("WRONG OBJECT ID");
            }


            try{ps.close();}catch(SQLException e){}


        }

    }

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<Office> getAll() throws Exception{

        System.out.println("Office getAll()");

        List<Office> objects=new ArrayList<Office>();
        List<Long> arr=new ArrayList<Long>();

        //System.out.println(consider(getRole(), 'r', 16));

        if((consider(getRole(), 'r', 16))==-1){

            return null;
        }


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

        try
        {
            this.connection.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public String getRole(){return this.role;}

    public void setRole(String role){
        this.role=role;
    }

    public int consider(String role, char mode, int type){


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

        System.out.println(column);

        try{

            check="SELECT "+column+" FROM types WHERE type_id=16";
            ps=connection.prepareStatement(check);
            System.out.println(check);
            rs=ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt(column));
                result=rs.getInt(column);
            }
            System.out.println("result_1:"+result);
            if(result==0){
                check="SELECT "+column+" FROM types WHERE type_id=14";
                ps=connection.prepareStatement(check);
                System.out.println(check);
                rs=ps.executeQuery();
                while(rs.next()){
                    //System.out.println(rs.getInt(column));
                    result=rs.getInt(column);
                }
                System.out.println("result_2:"+result);
            }

            if(result==0){
                check="SELECT "+column+" FROM types WHERE type_id=11";
                ps=connection.prepareStatement(check);
                System.out.println(check);
                rs=ps.executeQuery();
                while(rs.next()){
                    //system.out.println(rs.getInt(column));
                    result=rs.getInt(column);
                }
                System.out.println("result_3:"+result);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }


        if(result==1){
            System.out.println("APPROWED");
        }

        if(result==-1){
            System.out.println("DENIED");
        }

        return result;
    }


}
