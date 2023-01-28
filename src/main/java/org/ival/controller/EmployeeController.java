package org.ival.controller;

import org.ival.model.Employee;
import org.ival.model.EmployeeScore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeController {

    public Connection connection;

    public EmployeeController(){
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/green-cafe","postgres","adwed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @GET
    @Path("/manager/{id}")
    public List<Employee> get(@PathParam("id") Long nomer) {
        List<Employee> employees = new ArrayList<>();
            try (Statement statement = connection.createStatement()) {
                statement.execute("WITH RECURSIVE employe_tree " +
                        "AS ( SELECT id, name, manager_id  " +
                        "FROM employes WHERE id  = "+ nomer +
                        " UNION" +
                        " SELECT e.id, e.name, e.manager_id" +
                        " FROM employes e" +
                        " INNER JOIN employe_tree s ON s.id = e.manager_id )" +
                        " SELECT * FROM employe_tree");
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resultSet.getLong("id"));
                        employee.setName(resultSet.getString("name"));
                        employee.setManager_id(resultSet.getLong("manager_id"));
                        employees.add(employee);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return employees;
    }
    @GET
    @Path("/result/{id}")
    public List<EmployeeScore> getScore(@PathParam("id") Long nomer){
        List<EmployeeScore> employeeScore = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            statement.execute("WITH RECURSIVE employe_tree " +
                    "AS ( SELECT id, name, manager_id " +
                    "FROM employes WHERE id  = "+ nomer +
                    " UNION" +
                    " SELECT e.id, e.name, e.manager_id" +
                    " FROM employes e" +
                    " INNER JOIN employe_tree s ON s.id = e.manager_id )" +
                    " SELECT SUM(es.score)/COUNT(es.score) as score" +
                    " FROM " +
                    " public.employee_score es " +
                    " inner join employe_tree et " +
                    " on es.id = et.id");

            try (ResultSet resultSet = statement.getResultSet()) {
                while (resultSet.next()) {
                    EmployeeScore employee = new EmployeeScore();
                    employee.setScore(resultSet.getInt("score"));
                    employeeScore.add(employee);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeScore;
    }
}
