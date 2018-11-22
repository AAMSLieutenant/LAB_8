/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;


import java.sql.SQLException;
import java.util.List;
import logic.Employee;
/**
 *
 * @author Rostislav Stakhov
 */
public interface EmployeeDaoble {

    /** Объект для управления персистентным состоянием объекта Employee */


    /** Создает новую запись и соответствующий ей объект */
    public void create(Employee employee) throws Exception;

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public Employee read(long key) throws Exception;

    /** Сохраняет состояние объекта group в базе данных */
    public void update(long key, Employee employee) throws Exception;

    /** Удаляет запись об объекте из базы данных */
    public void delete(long key) throws Exception;

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<Employee> getAll() throws Exception;

    public void quit();

    /** Возвращает роль текущего пользователя базы данных
     * @return  роль пользователя (админ, менеджер, рядовой сотрудник)*/
    public String getRole();

    /** Задает роль пользователя в базе данных (админ, менеджер, простой сотрудник)
     * @param role - роль пользователя (админ, менеджер, рядовой сотрудник)*/
    public void setRole(String role);

    /** Проверяет, может ли сотрудник с данной ролью выполнять текущий запрос
     * @param role - роль пользователя (админ, менеджер, рядовой сотрудник)
     * @param mode - вид запроса (чтение - запись)
     * @param type - тип объекта
     */

    public int consider(String role, char mode, int type);

}
