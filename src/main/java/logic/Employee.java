/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.text.*;
import java.util.Date;

/**
 *
 * @author Rostislav Stakhov
 */
public class Employee {



    private Long id;
    private String obj_name;
    private Integer emp_num;
    private String emp_name;
    private String emp_job;
    private Date hire_date;
    private Double salary;
    private Long parent_object_id;

    public Employee(){

    }

    public Employee(String obj_name, int emp_num, String emp_name, String emp_job, String hire_date, Double salary, Long parent_object_id){
        try{
            this.obj_name=obj_name;
            this.emp_num=emp_num;
            this.emp_name=emp_name;
            this.emp_job=emp_job;
            this.hire_date=new SimpleDateFormat("dd.mm.yyyy").parse(hire_date);
            this.salary=salary;
            this.parent_object_id=parent_object_id;
        }
        catch(ParseException pe){

        }

    }


    public Long getId(){
        return id;
    }

    public void setId(Long id){

        this.id=id;
    }


    public String getObjName(){

        return obj_name;
    }

    public void setObjName(String obj_name){

        this.obj_name=obj_name;
    }

    public int getEmpNum()
    {
        return emp_num;
    }

    public void setEmpNum(int emp_num)
    {
        this.emp_num = emp_num;
    }

    public String getEmpName()
    {
        return emp_name;
    }

    public void setEmpName(String emp_name)
    {
        this.emp_name = emp_name;
    }

    public String getEmpJob()
    {
        return emp_job;
    }

    public void setEmpJob(String emp_job)
    {
        this.emp_job = emp_job;
    }

    public Date getEmpHireDate()
    {

        return hire_date;
    }

    public void setEmpHireDate(Date hire_date)
    {
        this.hire_date = hire_date;
    }

    public Double getSalary(){

        return salary;
    }

    public void setSalary(Double salary){

        this.salary=salary;
    }

    public Long getParObjId()
    {
        return parent_object_id;
    }

    public void setParObjId(Long parent_object_id)
    {
        this.parent_object_id = parent_object_id;
    }


}
