package main

import(
	"net/http"
	"log"
	"strconv"
	"fmt"
	"encoding/json"
	"github.com/gorilla/mux"
	
)
type Employee struct {
    ID   int    `json:"id"`
    Name string `json:"name"`
	Age int		`json:"age"`
	Address string `json:"address"`
}

func all_employees(w http.ResponseWriter, r *http.Request){
	var employees []Employee
	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		employees = mongo_db_read_all_employee_details()
	}else if database_name=="mysql"{
		employees, _ = mysql_db_get_all_employee()
	}else{
		employees, _ = mysql_db_get_all_employee()
		employees = append(employees, mongo_db_read_all_employee_details()...)
	}

	// fmt.Println(database_name)
	json.NewEncoder(w).Encode(employees)
    fmt.Println("Endpoint Hit: all_employees")
}

func unique_employee_details(w http.ResponseWriter, r *http.Request){
	employee_id_string:=mux.Vars(r)["id"]
	employee_id, err := strconv.Atoi(employee_id_string);
	if  err != nil {
		fmt.Fprintf(w, "Invalid employee ID")
	}
	var employees Employee
	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		employees, err = mongo_db_read_employee_details(int(employee_id))
	}else if database_name=="mysql"{
		employees, err = mysql_db_get_employee(int(employee_id))
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

func insert_employee_details(w http.ResponseWriter, r *http.Request){
	var employee Employee
	err := json.NewDecoder(r.Body).Decode(&employee)
    if err != nil {
        http.Error(w, err.Error(), http.StatusBadRequest)
        return
    }

	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		status, err := mongo_db_insert_new_employee(employee)
		if err != nil{
			fmt.Fprintf(w, status+ " : " +err.Error())
			return
		}
		fmt.Fprintf(w, status)
	}else if database_name=="mysql"{
		id, err := mysql_db_insert(employee.Name, employee.Age, employee.Address)
		if err != nil{
			fmt.Fprintf(w, err.Error())
			return
		}
		if id == 0{
			fmt.Fprintf(w, "Unsuccessful insert")
			return 
		}
		fmt.Fprintf(w, "Successfully inserted with id :"+ strconv.Itoa(id))
	}else{
		fmt.Fprintf(w,"Error, db name not found")
	}
	fmt.Println("Endpoint Hit: insert_employee_details")
}


func delete_employee_details(w http.ResponseWriter, r *http.Request){
	employee_id_string:=mux.Vars(r)["id"]
	employee_id, err := strconv.Atoi(employee_id_string);
	if  err != nil {
		fmt.Fprintf(w, "Invalid employee ID")
	}


	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		status, err := mongo_db_delete_employee_details(int(employee_id))
		if err!=nil{
			fmt.Fprintf(w, status +" : "+ err.Error())
			return
		}
		fmt.Fprintf(w, status )
	}else if database_name=="mysql"{
		count, err := mysql_db_delete_employee(int64(employee_id))
		if err !=nil{
			fmt.Fprintf(w, err.Error())
		}
		if count==0{
			fmt.Fprintf(w,"Delete not performed as there were no matching rows for this id")
			return
		}
		fmt.Fprintf(w,"Delete successful")
	}else{
		fmt.Fprintf(w,"Error, db name not found")
	}
	fmt.Println("Endpoint Hit: delete_employee_details")
}

func db_update_employee_details(w http.ResponseWriter, r *http.Request){
	var employee Employee
	err := json.NewDecoder(r.Body).Decode(&employee)
	if err != nil {
        http.Error(w, err.Error(), http.StatusBadRequest)
        return
    }
	employee_id_string:=mux.Vars(r)["id"]
	employee_id, err := strconv.Atoi(employee_id_string);
	if  err != nil {
		fmt.Fprintf(w, "Invalid employee ID")
	}


	database_name := r.URL.Query().Get("db")
	if database_name == "mongo"{
		status, err := mongo_db_update_employee_details(int(employee_id), employee.Name, employee.Age, employee.Address)
		if err!=nil{
			fmt.Fprintf(w, status +" : "+ err.Error())
			return
		}
		fmt.Fprintf(w, status)
	}else if database_name=="mysql"{
		count, err := mysql_db_update_employee_details(int64(employee_id), employee.Name, employee.Age, employee.Address)
		if err !=nil{
			fmt.Fprintf(w, err.Error())
		}
		if count==0{
			fmt.Fprintf(w, " : Update not performed as there were no matching rows for this id or the data is up-to-date")
			return
		}
		fmt.Fprintf(w, "Update successful")
	}else{
		fmt.Fprintf(w,"Error, db name not found")
	}
	fmt.Println("Endpoint Hit: db_update_employee_details")
}

func handleRequests() {
    myRouter := mux.NewRouter()
    myRouter.HandleFunc("/", all_employees).Methods("GET")
	myRouter.HandleFunc("/employee/{id}", unique_employee_details).Methods("GET")
	myRouter.HandleFunc("/employee", insert_employee_details).Methods("POST") // insert
	myRouter.HandleFunc("/employee/{id}", delete_employee_details).Methods("DELETE")
	myRouter.HandleFunc("/employee/{id}", db_update_employee_details).Methods("PUT") // update
    log.Fatal(http.ListenAndServe(":10000", myRouter))
}

func main() {
	handleRequests()

}