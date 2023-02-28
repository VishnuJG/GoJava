package main

import (
	"context"
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	// "Employee"
)

const (  
    username = "root"
    password = "Vishnu@2001"
    hostname = "127.0.0.1:3306"
    dbname   = "employeedb"
)

// type Employee struct {
//     ID   int    `json:"id"`
//     Name string `json:"name"`
// 	Age int		`json:"age"`
// 	Address string `json:"name"`
// }

func dsn() string {  
    return fmt.Sprintf("%s:%s@tcp(%s)/%s", username, password, hostname, dbname)
}

func mysql_db_Connection() (*sql.DB, error) {  
    db, err := sql.Open("mysql", dsn())
    if err != nil {
        fmt.Printf("Error %s when opening DB\n", err)
        return nil, err
    }
    return db, nil
}

func mysql_db_health_check()error{
	db, err := mysql_db_Connection()
	if err != nil {
        // panic(err.Error())
		return err
    }
	defer db.Close()
	health_check_query:="create table if not exists employee_details(id INT unsigned NOT NULL AUTO_INCREMENT, name varchar(150) NOT NULL, age INT, address LONGTEXT, PRIMARY KEY(id));"
	res, err := db.ExecContext(context.Background(), health_check_query)
	if err != nil {
		// fmt.Printf("Health check failer: %q\n", err)
		return err
	}
	rows, err := res.RowsAffected()
    if err != nil {
        // fmt.Printf("Error %q when getting rows affected\n", err)
        return err
    }
    fmt.Printf("Rows affected when creating table: %d\n", rows)
	return nil
}

func mysql_db_insert(ename string, eage int, eaddr string ) (int, error){
	db, err := mysql_db_Connection()
    // if there is an error opening the connection, handle it
    if err != nil {
        panic(err.Error())
		// return 0, err
    }

	defer db.Close()

	insert_query:="INSERT INTO employee_details(name, age, address) VALUES(?, ?, ?)"
	insert, err := db.ExecContext(context.Background(), insert_query, ename, eage, eaddr)
    if err != nil {
		fmt.Printf("Insert unsuccessful: %q\n", err)
		return 0, err
	}
	id, err := insert.LastInsertId()
	if err != nil {
		fmt.Printf("Unable to retrieve id: %q\n", err)
		return 0, err
	}

	return int(id), nil
}

func mysql_db_get_employee(id int)(Employee, error){
	get_query:="SELECT * FROM employee_details where id = ?;"
	db, err := mysql_db_Connection()
	var employee Employee
	if err != nil {
        // panic(err.Error())
		return employee, err
    }
	defer db.Close()
	err = db.QueryRow(get_query, id).Scan(&employee.ID, &employee.Name, &employee.Age, &employee.Address)
    if err !=nil {
        // panic(err.Error())
		return employee, err
    }
	
	// fmt.Println(employee.ID, employee.Name, employee.Age, employee.Address)
    return employee, nil
}

func mysql_db_get_all_employee()([]Employee, error){
	get_query:="SELECT * FROM employee_details;"
	db, err := mysql_db_Connection()
	var employees []Employee
	if err != nil {
        // panic(err.Error())
		return employees, err
    }
	defer db.Close()
	// err = db.Query(get_query).Scan(&employee.ID, &employee.Name, &employee.Age, &employee.Address)
	cur, err := db.Query(get_query)
    if err !=nil {
        // panic(err.Error())
		return employees, err
    }else{
		for cur.Next(){
			var employee Employee
			err := cur.Scan(&employee.ID, &employee.Name, &employee.Age, &employee.Address)
			if err != nil {
				fmt.Println(err)
				return employees, err
			}
			employees = append(employees, employee)
		}
		return employees, nil
	}
	
	// fmt.Println(employee.ID, employee.Name, employee.Age, employee.Address)
    return employees, nil
}

func mysql_db_update_employee_details(id int64, ename string, eage int, eaddr string ) (int, error){
	db, err := mysql_db_Connection()
    // if there is an error opening the connection, handle it
    if err != nil {
        panic(err.Error())
		return 0, err
    }

	defer db.Close()
	// insert, err := db.Query("INSERT INTO employee_details(name, age, address) VALUES('Vishnu J G', 21, '42, 2nd main, Poorna Pragna Layout BSK 3rd stage, Bengaluru 560085')")
	update_query:="UPDATE employee_details SET name = ?, age = ?, address = ? where id = ?"
	update, err := db.ExecContext(context.Background(), update_query, ename, eage, eaddr, id)
    if err != nil {
		fmt.Printf("Update unsuccessful: %q\n", err)
		return 0, err
	}
	rows, _ :=update.RowsAffected()
	if rows==0{
		return 0, fmt.Errorf("Unsuccessful Update")
	}
	fmt.Printf("Updated id: %v\n", id)
	return int(id), nil
}

func mysql_db_delete_employee(id int64) (int, error){
	db, err := mysql_db_Connection()
    // if there is an error opening the connection, handle it
    if err != nil {
        panic(err.Error())
		return 0, err
    }

	defer db.Close()
	// insert, err := db.Query("INSERT INTO employee_details(name, age, address) VALUES('Vishnu J G', 21, '42, 2nd main, Poorna Pragna Layout BSK 3rd stage, Bengaluru 560085')")
	delete_query:="DELETE FROM employee_details where id = ?"
	deleted, err := db.ExecContext(context.Background(), delete_query, id)
    if err != nil {
		fmt.Printf("Update unsuccessful: %q\n", err)
		return 0, err
	}
	rows, _ :=deleted.RowsAffected()
	if rows==0{
		return 0, fmt.Errorf("Delete Unsuccessful")
	}
	fmt.Printf("Deleted id: %v\n", id)
	return int(id), nil
}

// func main() {

// 	// Database health check
// 	err := db_health_check()
// 	if err != nil{
// 		fmt.Printf("Error : %q while performing database health check", err)
// 	}

// 	// Create/Insert employee commands
// 	id, err := db_insert("Vishnu J G", 21, "42, 2nd main, Poorna Pragna Layout BSK 3rd stage, Bengaluru 560085")	
// 	if err != nil {
//         fmt.Printf("Error: %q when fetching employee details\n", err)
        
//     } else {
// 		fmt.Printf("Inserted id: %v\n", id)
// 	}
	
// 	// Read employee details based on his ID commands
// 	employee_detail, err := db_get_employee(id)
// 	if err != nil {
//         fmt.Printf("Error: %q when fetching employee details\n", err)
        
//     } else {
// 		fmt.Println(employee_detail.ID, employee_detail.Name, employee_detail.Age, employee_detail.Address)
// 	}
	
// 	// Update employee details based on his ID commands
// 	id, err = db_update_employee_details(int64(id), "J G Vishnu", 22, "42, 2nd main, Poorna Pragna Layout BSK 3rd stage, Bengaluru 560085")
// 	if err != nil {
//         fmt.Printf("Error: %q when updating employee details\n", err)
        
//     } else {
// 		fmt.Println("Successfully updated employee details with ID : ", id)
// 	}

	
//     // Delete an employee from the db based on ID commands
// 	// id, err = db_delete_employee(int64(id))
// 	// if err != nil {
//     //     fmt.Printf("Error: %q when deleting employee details\n", err)
        
//     // } else {
// 	// 	fmt.Println("Successfully deleting employee details with ID : ", id)
// 	// }
// 	fmt.Println(db_get_all_employee())
// }