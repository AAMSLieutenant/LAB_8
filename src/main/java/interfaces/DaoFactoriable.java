package interfaces;

import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author Rostislav Stakhov
 */


/** Фабрика объектов для работы с базой данных */
public interface DaoFactoriable
{

    /** Возвращает подключение к базе данных */
    public Connection getConnection() throws SQLException;

    /** Возвращает объект для управления персистентным состоянием объекта Employee */
    public EmployeeDaoble getEmployeeDao() throws Exception;

    /** Возвращает объект для управления персистентным состоянием объекта Office */
    public OfficeDaoble getOfficeDao() throws Exception;
}/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


