/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.text.*;
import java.util.Date;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", obj_name='" + obj_name + '\'' +
                ", emp_num=" + emp_num +
                ", emp_name='" + emp_name + '\'' +
                ", emp_job='" + emp_job + '\'' +
                ", hire_date=" + hire_date +
                ", salary=" + salary +
                ", parent_object_id=" + parent_object_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(obj_name, employee.obj_name) &&
                Objects.equals(emp_num, employee.emp_num) &&
                Objects.equals(emp_name, employee.emp_name) &&
                Objects.equals(emp_job, employee.emp_job) &&
                Objects.equals(hire_date, employee.hire_date) &&
                Objects.equals(salary, employee.salary) &&
                Objects.equals(parent_object_id, employee.parent_object_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, obj_name, emp_num, emp_name, emp_job, hire_date, salary, parent_object_id);
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
