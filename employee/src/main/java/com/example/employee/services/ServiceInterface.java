package com.example.employee.services;
import java.util.*;


import com.example.employee.repository.EmployeeRepository;
// import com.example.employee.entity.EmployeeMongo;
import com.example.employee.entity.EmployeeMySQL;
import org.springframework.http.*;

public interface ServiceInterface {

    ResponseEntity<?> db_get_all_employee_details(EmployeeRepository employeeRepositor);
    ArrayList<EmployeeMySQL> db_get_all_employee_details_emp_format(EmployeeRepository employeeRepository);
    ResponseEntity<?> db_get_unique_employee_details(EmployeeRepository employeeRepository, int id);
    ResponseEntity<?> db_insert_employee_details(EmployeeRepository employeeRepository, String name, int age, String address);
    ResponseEntity<?> db_update_employee_details(EmployeeRepository employeeRepository, int id, EmployeeMySQL emp);
    ResponseEntity<?> db_delete_employee_details(EmployeeRepository employeeRepository, int id);
}
