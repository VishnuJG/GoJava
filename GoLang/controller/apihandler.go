package controller

import(
	"net/http"
	"strconv"
	"fmt"
	"encoding/json"
	"github.com/gorilla/mux"
	"employee.info/m/entity"
	// "employee.info/m/services"
	i "employee.info/m/interfaces"
	
)
// type Employee struct {
//     ID   int    `json:"id"`
//     Name string `json:"name"`
// 	Age int		`json:"age"`
// 	Address string `json:"address"`
// }



func All_employees(w http.ResponseWriter, r *http.Request){
	var employees []entity.Employee
	database_name := r.URL.Query().Get("db")
	
	if database_name == "mongo"{
		employeeService := &i.MongoEmployeeServiceImpl{}
		employees, _ = employeeService.DbReadAllEmployeeDetails()
	}else if database_name=="mysql"{
		employeeService := &i.MysqlEmployeeServiceImpl{}
		employees, _ = employeeService.DbReadAllEmployeeDetails()
	}else{
		mongoEmployeeService := &i.MongoEmployeeServiceImpl{}
		mysqlEmployeeService := &i.MongoEmployeeServiceImpl{}
		mysqlemployees, _ := mysqlEmployeeService.DbReadAllEmployeeDetails()
		mongoemp, _:=mongoEmployeeService.DbReadAllEmployeeDetails()
		employees = append(mysqlemployees, mongoemp...)
	}

	json.NewEncoder(w).Encode(employees)
    fmt.Println("Endpoint Hit: all_employees")
}

func Unique_employee_details(w http.ResponseWriter, r *http.Request){
	employee_id_string:=mux.Vars(r)["id"]
	employee_id, err := strconv.Atoi(employee_id_string);
	
	if  err != nil {
		fmt.Fprintf(w, "Invalid employee ID")
	}
	var employees entity.Employee
	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		employeeService := &i.MongoEmployeeServiceImpl{}
		// employees, err = services.Mongo_db_read_employee_details(int(employee_id))
		employees, err = employeeService.DbReadEmployeeDetails(int(employee_id))
	}else if database_name=="mysql"{
		employeeService := &i.MysqlEmployeeServiceImpl{}
		employees, err = employeeService.DbReadEmployeeDetails(int(employee_id))
	}else{
		fmt.Fprintf(w,"Error, db name not found")
	}
	if employees.ID==0{
		fmt.Fprintf(w, err.Error()+": User not found for this id :"+ employee_id_string)
		return
	}
	json.NewEncoder(w).Encode(employees)
    fmt.Println("Endpoint Hit: unique_employee_details")
}

func Insert_employee_details(w http.ResponseWriter, r *http.Request){
	var employee entity.Employee
	err := json.NewDecoder(r.Body).Decode(&employee)
	
    if err != nil {
        http.Error(w, err.Error(), http.StatusBadRequest)
        return
    }

	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		employeeService := &i.MongoEmployeeServiceImpl{}
		// status, err := services.Mongo_db_insert_new_employee(employee)
		status, err := employeeService.DbInsertNewEmployee(employee)
		if err != nil{
			fmt.Fprintf(w, status+ " : " +err.Error())
			return
		}
		fmt.Fprintf(w, status)
	}else if database_name=="mysql"{
		employeeService := &i.MysqlEmployeeServiceImpl{}
		status, err := employeeService.DbInsertNewEmployee(employee)
		if err != nil{
			fmt.Fprintf(w, status +" : " + err.Error())
			return
		}
		// if id == 0{
		// 	fmt.Fprintf(w, "Unsuccessful insert")
		// 	return 
		// }
		fmt.Fprintf(w, status)
	}else{
		fmt.Fprintf(w,"Error, db name not found")
	}
	fmt.Println("Endpoint Hit: insert_employee_details")
}


func Delete_employee_details(w http.ResponseWriter, r *http.Request){
	employee_id_string:=mux.Vars(r)["id"]
	employee_id, err := strconv.Atoi(employee_id_string)
	
	if  err != nil {
		fmt.Fprintf(w, "Invalid employee ID")
	}


	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		employeeService := &i.MongoEmployeeServiceImpl{}
		// status, err := services.Mongo_db_delete_employee_details(int(employee_id))
		status, err := employeeService.DbDeleteEmployeeDetails(int(employee_id))
		if err!=nil{
			fmt.Fprintf(w, status +" : "+ err.Error())
			return
		}
		fmt.Fprintf(w, status )
	}else if database_name=="mysql"{
		employeeService := &i.MysqlEmployeeServiceImpl{}
		status, err := employeeService.DbDeleteEmployeeDetails(int(employee_id))
		if err !=nil{
			fmt.Fprintf(w, status +" : "+err.Error())
			return
		}
		
		fmt.Fprintf(w, status)
	}else{
		fmt.Fprintf(w,"Error, db name not found")
	}
	fmt.Println("Endpoint Hit: delete_employee_details")
}

func Db_update_employee_details(w http.ResponseWriter, r *http.Request){
	var employee entity.Employee
	err := json.NewDecoder(r.Body).Decode(&employee)
	
	if err != nil {
        http.Error(w, err.Error(), http.StatusBadRequest)
        return
    }
	employee_id_string:=mux.Vars(r)["id"]
	employee_id, err := strconv.Atoi(employee_id_string);
	if  err != nil {
		fmt.Fprintf(w, "Invalid employee ID")
		return
	}


	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		employeeService := &i.MongoEmployeeServiceImpl{}
		// status, err := services.Mongo_db_update_employee_details(int(employee_id), employee.Name, employee.Age, employee.Address)
		status, err := employeeService.DbUpdateEmployeeDetails(int(employee_id), employee.Name, employee.Age, employee.Address)
		if err!=nil{
			fmt.Fprintf(w, status +" : "+ err.Error())
			return
		}
		fmt.Fprintf(w, status)
	}else if database_name=="mysql"{
		employeeService := &i.MysqlEmployeeServiceImpl{}
		status, err := employeeService.DbUpdateEmployeeDetails(int(employee_id), employee.Name, employee.Age, employee.Address)
		if err !=nil{
			fmt.Fprintf(w, status +" : " + err.Error())
			return
		}
		
		fmt.Fprintf(w, status)
	}else{
		fmt.Fprintf(w,"Error, db name not found")
	}
	fmt.Println("Endpoint Hit: db_update_employee_details")
}

