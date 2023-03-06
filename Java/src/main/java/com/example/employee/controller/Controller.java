package com.example.employee.controller;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.example.employee.entity.EmployeeMySQL;
// import com.example.employee.entity.EmployeeMongo;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.services.MongoFunc;
import com.example.employee.services.MySQLfunc;
import com.google.gson.Gson;


@RestController
@CrossOrigin(origins="*")
public class Controller {
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/")
	public ResponseEntity<?> all_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db) {
		Gson gson = new Gson();
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				// ArrayList<EmployeeMySQL> all_emp = newob.db_get_all_employee_details();
				// String json = gson.toJson(all_emp); 
				// if (all_emp.size()==0){
				// 	throw new Exception("No users found");
				// }
				// return ResponseEntity.ok(json);
				return newob.db_get_all_employee_details(employeeRepository);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
			
		}
		else if(db.equals("mongo")){
			try{
			
				MongoFunc mongoobj = new MongoFunc();
				return mongoobj.db_get_all_employee_details(employeeRepository);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else{
			try{
				MySQLfunc newob = new MySQLfunc();
				MongoFunc mongoobj = new MongoFunc();
				ArrayList<EmployeeMySQL> mysqlemp = newob.db_get_all_employee_details_emp_format(employeeRepository);
				ArrayList<EmployeeMySQL> mongo_emp=mongoobj.db_get_all_employee_details_emp_format(employeeRepository);
				mysqlemp.addAll(mongo_emp);
				if (mysqlemp.size()==0){
					throw new Exception("No users found");
				}
				String json = gson.toJson(mysqlemp);
				return ResponseEntity.ok(json);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<?> unique_employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {

		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				// EmployeeMySQL emp = newob.db_get_unique_employee_details(employeeRepository, Integer.parseInt(id));
				// if (emp.getName() == null){
				// 	throw new Exception("User not found"); 
				// }
				// String json = gson.toJson(emp); 
				// return ResponseEntity.ok(json);
				return newob.db_get_unique_employee_details(employeeRepository, Integer.parseInt(id));
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else if(db.equals("mongo")){
			
			MongoFunc mongoobj = new MongoFunc();
			return mongoobj.db_get_unique_employee_details(employeeRepository, Integer.parseInt(id));
		}
		else{
			return ResponseEntity.ok("Please specify the proper db name !!!");
		}
	}

	@PostMapping(path = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insert_employee_handler(@RequestBody EmployeeMySQL newemp, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				// int rowsaffected = newob.db_insert_employee_details(employeeRepository, newemp);
				// if(rowsaffected==0){
				// 	throw new Exception("Insert failed");   
				// }
				// return ResponseEntity.ok("Successfully inserted");
				return newob.db_insert_employee_details(employeeRepository, newemp.name, newemp.age, newemp.address);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else if(db.equals("mongo")){
			try{

				MongoFunc mongoobj = new MongoFunc();
				
				return mongoobj.db_insert_employee_details(employeeRepository , newemp.name, newemp.age, newemp.address);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}else{
			return ResponseEntity.ok("Please specify the proper db name !!!");
		}
		
	}

	@PutMapping(path = "/employee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update_employee_handler(@PathVariable("id") String id, @RequestBody EmployeeMySQL emp, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				// EmployeeMySQL checkemp = newob.db_get_employee_details(Integer.parseInt(id));
				// if(checkemp.name.equals(emp.name) && checkemp.address.equals(emp.address) && checkemp.age == emp.age){
				// 	throw new Exception("Update failed"); 
				// }
				// int updates = newob.db_update_employee_details(Integer.parseInt(id), emp.name, emp.age, emp.address);
				// System.out.println(updates);
				// if(updates == 0){
				// 	throw new Exception("Update failed"); 
				// }
				// return ResponseEntity.ok("Successfully updated");
				return newob.db_update_employee_details(employeeRepository, Integer.parseInt(id), emp);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}else if(db.equals("mongo")){
			try{
				
				MongoFunc mongoob = new MongoFunc();
				return mongoob.db_update_employee_details(employeeRepository, Integer.parseInt(id), emp);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else{
			return ResponseEntity.ok("Error while updating");
		}
		
		
	}
	
	@DeleteMapping(path = "/employee/{id}")
	public ResponseEntity<?> delete_employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				// int deletes = newob.db_delete_employee_details(Integer.parseInt(id));
				// if (deletes == 0){
				// 	throw new Exception("Delete failed"); 
				// }
				// return ResponseEntity.ok("Successfully deleted");
				return newob.db_delete_employee_details(employeeRepository, Integer.parseInt(id));
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else if(db.equals("mongo")){
			try{
				MongoFunc mongoob = new MongoFunc();
				return mongoob.db_delete_employee_details(employeeRepository, Integer.parseInt(id));

			}
			catch(Exception ex){
				return ResponseEntity.ok(ex.getMessage());
			}
		}
		else{
			return ResponseEntity.ok("Please specify the proper db name !!!");
		}
		
	}
}