package GoJava.employee.src.main.java.springboot.api.employee;
import java.util.ArrayList;

import java.sql.*;



public class MySQLfunc {
    public ArrayList<Employee> db_get_all_employee_details(){
        ArrayList<Employee> employees = new ArrayList<>(); 
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
            Employee emp;
            while (resultSet.next()) {

                id = resultSet.getInt("id");
                name = resultSet.getString("name").trim();
                age = resultSet.getInt("age");
                address = resultSet.getString("address").trim();
                emp= new Employee(id, name, age, address);
                // System.out.println("Id : " + id+ " Name : " + name+ " Age : " + age+ " Address : " + address);
                employees.add(emp);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return employees;
        }
        catch (Exception exception) {
			System.out.println(exception);
		}
        return employees;
    }

    public Employee db_get_employee_details(int id){
        Employee emp = new Employee();
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
                // System.out.println("Id : " + id+ " Name : " + name+ " Age : " + age+ " Address : " + address);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return emp;
        }
        catch (Exception exception) {
			System.out.println(exception);
		}
        return emp;
    }

    public void db_insert_employee_details(String name, int age, String address){
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
            Statement statement;
            statement = connection.createStatement();
            statement.executeUpdate("INSERT into employee_details(name, age, address) VALUES('" + name+"',"+String.valueOf(age)+",'"+address+"');");

            statement.close();
            connection.close();
        }
        catch (Exception exception) {
			System.out.println(exception);
		}
    }

    public void db_update_employee_details(int id, String name, int age, String address){
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
           
            String query="UPDATE employee_details set name = ?, age = ?, address=? where id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, address);
            statement.setInt(4, id);
            statement.execute();
          

            statement.close();
            connection.close();
        }
        catch (Exception exception) {
			System.out.println(exception);
		}
    }

    public void db_delete_employee_details(int id){
        try{
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root", "Vishnu@2001");
           
            String query="DELETE from employee_details where id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            // statement.executeUpdate("UPDATE employee_details set name = ?, age = ?, address=? where id = ?;");
            statement.execute();
            // statement.close();
            connection.close();
        }
        catch (Exception exception) {
			System.out.println(exception);
		}
    }

	public static void main(String arg[])
	{
		try {
            // db_get_all_employee_details();
            // db_update_employee_details(50, "Raghavendra", 28, "Somewhere in America");
            // Employee emp = db_get_employee_details(50);
            // System.out.println(emp);
            // db_insert_employee_details("Mr Bean", 45, "Somewhere in Europe");
            // db_delete_employee_details(56);

			
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	} 
}
