package org.ival.controller;

import org.ival.model.Employee;
import org.ival.model.FactorialModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("factorial")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FactorialController {

    public Connection connection;

    public FactorialController(){
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/green-cafe","postgres","adwed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{input}")
    public Response get(@PathParam("input")Long input){
        List<FactorialModel> factorial = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            statement.execute("WITH RECURSIVE cte (n, factorial) AS (" +
                    " VALUES (0, 1)" +
                    " UNION ALL" +
                    " SELECT n+1, (n+1)*factorial FROM cte WHERE n < "+ input+")" +
                    " SELECT * FROM cte");
            try (ResultSet resultSet = statement.getResultSet()) {
                while (resultSet.next()) {
                    FactorialModel factorialModel = new FactorialModel();
                    factorialModel.setN(resultSet.getInt("n"));
                    factorialModel.setFactorial(resultSet.getInt("factorial"));
                    factorial.add(factorialModel);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.ok(factorial).build();
    }
}
