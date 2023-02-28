package Tests.employee;

import java.util.*;

import javax.lang.model.type.ErrorType;
import org.springframework.dao.EmptyResultDataAccessException;
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

// import com.example.accessingdatamongodb.Employee;
import com.google.gson.Gson;


@RestController
@CrossOrigin(origins="*")
public class HelloController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/")
	public ResponseEntity<?> all_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db) {
		Gson gson = new Gson();
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				ArrayList<Employee> temp_emp = newob.db_get_all_employee_details();
				String json = gson.toJson(temp_emp); 
				if (temp_emp.size()==0){
					throw new Exception("No users found");
				}
				return ResponseEntity.ok(json);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
			
		}
		else if(db.equals("mongo")){
			try{
				ArrayList<Employee> mysqlemp = new ArrayList<>();
				List<Employee2> temp_emp=employeeRepository.findAll();
				for(Employee2 emp : temp_emp){
					Employee newemp=new Employee(emp.id, emp.name, emp.age, emp.address);
					mysqlemp.add(newemp);
				}
				return ResponseEntity.ok(gson.toJson(mysqlemp));
				// return "Hello";
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else{
			try{
				MySQLfunc newob = new MySQLfunc();
				
				ArrayList<Employee> mysqlemp = newob.db_get_all_employee_details();
				List<Employee2> temp_emp=employeeRepository.findAll();
				
				for(Employee2 emp : temp_emp){
					Employee newemp=new Employee(emp.id, emp.name, emp.age, emp.address);
					mysqlemp.add(newemp);
				}
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
		Gson gson = new Gson();
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				Employee emp = newob.db_get_employee_details(Integer.parseInt(id));
				if (emp.getName() == null){
					throw new Exception("User not found"); 
				}
				String json = gson.toJson(emp); 
				// return json;

				return ResponseEntity.ok(json);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else if(db.equals("mongo")){
			try{
				// return gson.toJson(employeeRepository.findById(Integer.parseInt(id)));
				String userdata = gson.toJson(employeeRepository.findById(Integer.parseInt(id)));
				if(userdata.equals("null") ){
					throw new Exception("User not found"); 
				}
				System.out.println(userdata);
				return ResponseEntity.ok(userdata);
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else{
			return ResponseEntity.ok("Please specify the proper db name !!!");
		}
	}

	@PostMapping(path = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insert_employee_handler(@RequestBody Employee newemp, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				int rowsaffected = newob.db_insert_employee_details(newemp.name, newemp.age, newemp.address);
				if(rowsaffected==0){
					throw new Exception("Insert failed");   
				}
				return ResponseEntity.ok("Successfully inserted");
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else if(db.equals("mongo")){
			try{
				List<Employee2> temp_emp=employeeRepository.findAll();
				int maxint=0;
				for (Employee2 emp : temp_emp){
					if(emp.id > maxint){
						maxint=emp.id;
					}
				}
				
				employeeRepository.save(new Employee2(maxint+1, newemp.name, newemp.age, newemp.address));
				return ResponseEntity.ok("Successfully inserted");
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}else{
			return ResponseEntity.ok("Please specify the proper db name !!!");
		}
		
	}

	@PutMapping(path = "/employee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update_employee_handler(@PathVariable("id") String id, @RequestBody Employee emp, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
		if (db.equals("mysql")){
			try{
				MySQLfunc newob = new MySQLfunc();
				Employee checkemp = newob.db_get_employee_details(Integer.parseInt(id));
				if(checkemp.name.equals(emp.name) && checkemp.address.equals(emp.address) && checkemp.age == emp.age){
					throw new Exception("Update failed"); 
				}
				int updates = newob.db_update_employee_details(Integer.parseInt(id), emp.name, emp.age, emp.address);
				System.out.println(updates);
				if(updates == 0){
					throw new Exception("Update failed"); 
				}
				return ResponseEntity.ok("Successfully updated");
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}else if(db.equals("mongo")){
			try{
				Employee userdata = employeeRepository.findById(Integer.parseInt(id));
				// Employee checkemp = new Employee(userdata.id, userdata.name, userdata.age, useradata.address);
				if(userdata.name.equals(emp.name) && userdata.address.equals(emp.address) && userdata.age == emp.age){
					throw new Exception("Update failed"); 
				}
				employeeRepository.deleteById(Integer.parseInt(id));
				employeeRepository.save(new Employee2(Integer.parseInt(id), emp.name, emp.age, emp.address));
				return ResponseEntity.ok("Successfully Updated!!!");
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
				int deletes = newob.db_delete_employee_details(Integer.parseInt(id));
				if (deletes == 0){
					throw new Exception("Delete failed"); 
				}
				return ResponseEntity.ok("Successfully deleted");
			}
			catch(Exception e){
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else if(db.equals("mongo")){
			try{
				if(employeeRepository.existsById(Integer.parseInt(id))){
					employeeRepository.deleteById(Integer.parseInt(id));
				}else{
					throw new Exception("Delete failed : Id not found"); 
				}
				
				return ResponseEntity.ok("Successfully deleted");
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