package com.example.employee.services;
import java.util.*;

import com.example.employee.entity.EmployeeMySQL;
import com.example.employee.entity.EmployeeMongo;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.http.*;
import com.google.gson.Gson;

public class MongoFunc implements ServiceInterface{
    
    Gson gson = new Gson();

    @Override
    public ResponseEntity<?> db_get_all_employee_details(EmployeeRepository employeeRepository){
        ArrayList<EmployeeMySQL> mysqlemp = new ArrayList<>();
        try{
            List<EmployeeMongo> temp_emp=employeeRepository.findAll();
            for(EmployeeMongo emp : temp_emp){
                EmployeeMySQL newemp=new EmployeeMySQL(emp.id, emp.name, emp.age, emp.address);
                mysqlemp.add(newemp);
                System.out.println(mysqlemp);
            }
            return ResponseEntity.ok(gson.toJson(mysqlemp));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }
    
    @Override
    public ArrayList<EmployeeMySQL> db_get_all_employee_details_emp_format(EmployeeRepository employeeRepository){
        ArrayList<EmployeeMySQL> mysqlemp = new ArrayList<>();
        try{
            List<EmployeeMongo> temp_emp=employeeRepository.findAll();
            for(EmployeeMongo emp : temp_emp){
                EmployeeMySQL newemp=new EmployeeMySQL(emp.id, emp.name, emp.age, emp.address);
                mysqlemp.add(newemp);
                System.out.println(mysqlemp);
            }
            return mysqlemp;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return mysqlemp;
        }
    }

    @Override
    public ResponseEntity<?> db_insert_employee_details(EmployeeRepository employeeRepository, String name, int age, String address){
        
        List<EmployeeMongo> all_emp=employeeRepository.findAll();
        try{
            int maxint=0;
            for (EmployeeMongo emp : all_emp){
                if(emp.id > maxint){
                    maxint=emp.id;
                }
            }
            employeeRepository.save(new EmployeeMongo(maxint+1, name, age, address));
            return ResponseEntity.ok("Successsful inserted employee with id : "+(maxint+1));

        }
        catch (Exception e) {
			System.out.println(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
		}
    }
    
    
    public ResponseEntity<?> db_get_unique_employee_details(EmployeeRepository employeeRepository, int id){
        try{

            String userdata = gson.toJson(employeeRepository.findById(id));
            if(userdata.equals("null") ){
                throw new Exception("User not found"); 
            }
            return ResponseEntity.ok(userdata);
        }
        catch(Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }


    public ResponseEntity<?> db_update_employee_details(EmployeeRepository employeeRepository, int id, EmployeeMySQL emp){
        EmployeeMySQL userdata = employeeRepository.findById(id);
        if(userdata.name.equals(emp.name) && userdata.address.equals(emp.address) && userdata.age == emp.age){
            // throw new Exception("Update failed"); 
            return ResponseEntity.ok("Update failed");
        }
        employeeRepository.deleteById(id);
        employeeRepository.save(new EmployeeMongo(id, emp.name, emp.age, emp.address));
        return ResponseEntity.ok("Successfully Updated!!!");
    }

    public ResponseEntity<?> db_delete_employee_details(EmployeeRepository employeeRepository, int id){
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        }else{
            return ResponseEntity.ok("Delete failed");
        }
        
        return ResponseEntity.ok("Successfully deleted");
    }
}
