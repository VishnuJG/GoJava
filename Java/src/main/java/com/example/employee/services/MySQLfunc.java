package com.example.employee.services;
import java.util.ArrayList;

import com.example.employee.repository.EmployeeRepository;
import com.example.employee.entity.EmployeeMySQL;
import org.springframework.http.*;
import com.google.gson.Gson;
import java.sql.*;



public class MySQLfunc implements ServiceInterface{
    
    Gson gson = new Gson();

    @Override
    public ResponseEntity<?> db_get_all_employee_details(EmployeeRepository employeeRepositor){
        ArrayList<EmployeeMySQL> employees = new ArrayList<>(); 
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from employee_details"); 
            int id;
            String name;
            int age;
            String address;
            EmployeeMySQL emp;
            while (resultSet.next()) {

                id = resultSet.getInt("id");
                name = resultSet.getString("name").trim();
                age = resultSet.getInt("age");
                address = resultSet.getString("address").trim();
                emp= new EmployeeMySQL(id, name, age, address);
                employees.add(emp);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return ResponseEntity.ok(gson.toJson(employees));
        }
        catch (Exception exception) {
			return ResponseEntity.ok("Fetch Failed, now employees found!");
		}
        // return ResponseEntity.ok(gson.toJson(employees));
    }

    public ArrayList<EmployeeMySQL> db_get_all_employee_details_emp_format(EmployeeRepository employeeRepositor){
        ArrayList<EmployeeMySQL> employees = new ArrayList<>(); 
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from employee_details"); 
            int id;
            String name;
            int age;
            String address;
            EmployeeMySQL emp;
            while (resultSet.next()) {

                id = resultSet.getInt("id");
                name = resultSet.getString("name").trim();
                age = resultSet.getInt("age");
                address = resultSet.getString("address").trim();
                emp= new EmployeeMySQL(id, name, age, address);
                employees.add(emp);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return employees;
        }
        catch (Exception exception) {
			return employees;
		}
        // return ResponseEntity.ok(gson.toJson(employees));
    }

    public ResponseEntity<?> db_get_unique_employee_details(EmployeeRepository employeeRepository, int id){
        EmployeeMySQL emp = new EmployeeMySQL();
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from employee_details where id =" + String.valueOf(id));
            
            String name;
            int age;
            String address;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                emp.setId(id);
                name = resultSet.getString("name").trim();
                emp.setName(name);
                age = resultSet.getInt("age");
                emp.setAge(age);
                address = resultSet.getString("address").trim();
                emp.setAddress(address);
            }
            resultSet.close();
            statement.close();
            connection.close();
            // System.out.println(emp);
            if(emp.id==null){
                throw new Exception("User not found");   
            }
            return ResponseEntity.ok(gson.toJson(emp));
        }
        catch (Exception exception) {
			System.out.println(exception);
            return ResponseEntity.ok("Unable to fetch employee details!");
		}
        // return emp;
    }

    public ResponseEntity<?> db_insert_employee_details(EmployeeRepository employeeRepository, String name, int age, String address){
        int rowsaffected = 0;
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
            Statement statement;
            statement = connection.createStatement();
            
            rowsaffected = statement.executeUpdate("INSERT into employee_details(name, age, address) VALUES('" + name+"',"+String.valueOf(age)+",'"+address+"');");

            statement.close();
            connection.close();
            if (rowsaffected==0){
                throw new Exception("Insert failed");   
            }
            return ResponseEntity.ok("Insert successful");
            
        }
        catch (Exception exception) {
			System.out.println(exception);
            return ResponseEntity.ok(exception.getMessage());
		}
    }

    public ResponseEntity<?> db_update_employee_details(EmployeeRepository employeeRepository, int id, EmployeeMySQL emp){
        int rowsaffected = 0;
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
           
            String query="UPDATE employee_details SET name = ?, age = ?, address = ? WHERE id = ? AND (name <> ? OR age <> ? OR address <> ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, emp.name);
            statement.setInt(2, emp.age);
            statement.setString(3, emp.address);
            statement.setInt(4, id);
            statement.setString(5, emp.name);
            statement.setInt(6, emp.age);
            statement.setString(7, emp.address);
            rowsaffected = statement.executeUpdate();
          

            statement.close();
            connection.close();
            System.out.println(rowsaffected);
            if (rowsaffected==0){
                throw new Exception("Update failed");   
            }else{
                return ResponseEntity.ok("Update successful");
            }
            
        }
        catch (Exception exception) {
			System.out.println(exception);
            return ResponseEntity.ok(exception.getMessage());
		}
    }

    public ResponseEntity<?> db_delete_employee_details(EmployeeRepository employeeRepository, int id){
        int rowsaffected = 0;
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
           
            String query="DELETE from employee_details where id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            rowsaffected = statement.executeUpdate();
            // statement.close();
            connection.close();
            if (rowsaffected==0){
                throw new Exception("Delete failed");   
            }
            return ResponseEntity.ok("Delete successful");

        }
        catch (Exception exception) {
			System.out.println(exception);
            return ResponseEntity.ok(exception.getMessage());
		}
    }

}
