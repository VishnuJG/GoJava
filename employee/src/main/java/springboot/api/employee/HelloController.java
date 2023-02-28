package GoJava.employee.src.main.java.springboot.api.employee;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.json.JSONObject;
import com.google.gson.Gson;


@RestController
// @RequestMapping(path="/JSON", produces="application/json")
@CrossOrigin(origins="*")
public class HelloController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/")
	public String all_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db) {
		Gson gson = new Gson();
		if (db.equals("mysql")){
			MySQLfunc newob = new MySQLfunc();
			String json = gson.toJson(newob.db_get_all_employee_details()); 
			return json;
		}
		else if(db.equals("mongo")){
			
			ArrayList<Employee> mysqlemp = new ArrayList<>();
			List<Employee2> temp_emp=employeeRepository.findAll();
			for(Employee2 emp : temp_emp){
				Employee newemp=new Employee(emp.id, emp.name, emp.age, emp.address);
				mysqlemp.add(newemp);
			}
			return gson.toJson(mysqlemp);
			// return "Hello";
		}
		else{
			MySQLfunc newob = new MySQLfunc();
			
			ArrayList<Employee> mysqlemp = newob.db_get_all_employee_details();
			List<Employee2> temp_emp=employeeRepository.findAll();
			for(Employee2 emp : temp_emp){
				Employee newemp=new Employee(emp.id, emp.name, emp.age, emp.address);
				mysqlemp.add(newemp);
			}
			String json = gson.toJson(mysqlemp);
			return json;
		}

		
	}

	@GetMapping("/employee/{id}")
	public String unique_employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		Gson gson = new Gson();
		if (db.equals("mysql")){
			MySQLfunc newob = new MySQLfunc();
			
			String json = gson.toJson(newob.db_get_employee_details(Integer.parseInt(id))); 
			return json;
		}
		else if(db.equals("mongo")){
			
			return gson.toJson(employeeRepository.findById(Integer.parseInt(id)));
			// return "Working wait da";
		}
		else{
			MySQLfunc newob = new MySQLfunc();
			
			ArrayList<Employee> mysqlemp = newob.db_get_all_employee_details();
			List<Employee2> temp_emp=employeeRepository.findAll();
			for(Employee2 emp : temp_emp){
				Employee newemp=new Employee(emp.id, emp.name, emp.age, emp.address);
				mysqlemp.add(newemp);
			}
			String json = gson.toJson(mysqlemp);
			return json;
		}
		// return "mongo still under development";
	}

	@PostMapping(path = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String insert_employee_handler(@RequestBody Employee newemp, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			MySQLfunc newob = new MySQLfunc();
			newob.db_insert_employee_details(newemp.name, newemp.age, newemp.address);
			return "Successfully inserted";
		}
		else if(db.equals("mongo")){
			List<Employee2> temp_emp=employeeRepository.findAll();
			int maxint=0;
			for (Employee2 emp : temp_emp){
				if(emp.id > maxint){
					maxint=emp.id;
				}
			}
			
			employeeRepository.save(new Employee2(maxint+1, newemp.name, newemp.age, newemp.address));
			return String.valueOf(maxint);
		}else{
			return "Please specify the proper db name !!!";
		}
		
	}

	@PutMapping(path = "/employee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String update_employee_handler(@PathVariable("id") String id, @RequestBody Employee emp, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			MySQLfunc newob = new MySQLfunc();
			newob.db_update_employee_details(Integer.parseInt(id), emp.name, emp.age, emp.address);
			// Gson gson = new Gson();
			// String json = gson.toJson(newob.db_get_employee_details(Integer.parseInt(id))); 
			return "Successfully updated";
		}else if(db.equals("mongo")){
			employeeRepository.deleteById(Integer.parseInt(id));
			employeeRepository.save(new Employee2(Integer.parseInt(id), emp.name, emp.age, emp.address));
			return "Successfully Updated!!!";
		}
		else{
			return "Error while updating";
		}
		
		
	}
	@DeleteMapping(path = "/employee/{id}")
	public String delete_employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			MySQLfunc newob = new MySQLfunc();
			newob.db_delete_employee_details(Integer.parseInt(id));
			return "Successfully deleted";
		}
		else if(db.equals("mongo")){
			employeeRepository.deleteById(Integer.parseInt(id));
			
			return "Successfully deleted";
		}
		else{
			return "Please specify the proper db name !!!";
		}
		
	}
}