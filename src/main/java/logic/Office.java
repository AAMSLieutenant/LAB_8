/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Rostislav Stakhov <a1c32mr@mail.ru>
 */
public class Office {

    private Long id;
    private String obj_name;
    private Integer emp_count;

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", obj_name='" + obj_name + '\'' +
                ", emp_count=" + emp_count +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Objects.equals(id, office.id) &&
                Objects.equals(obj_name, office.obj_name) &&
                Objects.equals(emp_count, office.emp_count) &&
                Objects.equals(location, office.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, obj_name, emp_count, location);
    }

    private String location;


    public Office(){

    }

    public Office(String obj_name, int emp_count, String location){

        this.obj_name=obj_name;
        this.emp_count=emp_count;
        this.location=location;

    }

    public Long getObjId(){
        return id;
    }

    public void setObjId(Long id){
        this.id=id;
    }

    public String getObjName(){
        return obj_name;
    }

    public void setObjName(String obj_name){
        this.obj_name = obj_name;
    }

    public Integer getEmpCount(){
        return emp_count;
    }

    public void setEmpCount(int emp_count){
        this.emp_count = emp_count;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

}