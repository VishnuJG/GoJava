package services

import (
	"context"
	"strconv"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"employee.info/m/entity"
	"employee.info/m/connections"
)


func Mysql_db_health_check()error{
	db, err := connections.Mysql_db_Connection()
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

func Mysql_db_insert(ename string, eage int, eaddr string ) (string, error){
	db, err := connections.Mysql_db_Connection()
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
		return "Failed to insert", err
	}
	id, err := insert.LastInsertId()
	if err != nil {
		fmt.Printf("Unable to retrieve id: %q\n", err)
		return "Failed to insert", err
	}

	return "Successfully inserted employee with id : "+strconv.Itoa(int(id)), nil
}

func Mysql_db_get_employee(id int)(entity.Employee, error){
	get_query:="SELECT * FROM employee_details where id = ?;"
	db, err := connections.Mysql_db_Connection()
	var employee entity.Employee
	if err != nil {

		return employee, err
    }
	defer db.Close()
	err = db.QueryRow(get_query, id).Scan(&employee.ID, &employee.Name, &employee.Age, &employee.Address)
    if err !=nil {

		return employee, err
    }
	

    return employee, nil
}

func Mysql_db_get_all_employee()([]entity.Employee, error){
	get_query:="SELECT * FROM employee_details;"
	db, err := connections.Mysql_db_Connection()
	var employees []entity.Employee
	if err != nil {
        // panic(err.Error())
		return employees, err
    }
	defer db.Close()

	cur, err := db.Query(get_query)
    if err !=nil {

		return employees, err
    }else{
		for cur.Next(){
			var employee entity.Employee
			err := cur.Scan(&employee.ID, &employee.Name, &employee.Age, &employee.Address)
			if err != nil {
				fmt.Println(err)
				return employees, err
			}
			employees = append(employees, employee)
		}
		return employees, nil
	}
	
}

func Mysql_db_update_employee_details(id int64, ename string, eage int, eaddr string ) (string, error){
	db, err := connections.Mysql_db_Connection()
    // if there is an error opening the connection, handle it
    if err != nil {
        // panic(err.Error())
		return "Failed to update", err
    }

	defer db.Close()
	// insert, err := db.Query("INSERT INTO employee_details(name, age, address) VALUES('Vishnu J G', 21, '42, 2nd main, Poorna Pragna Layout BSK 3rd stage, Bengaluru 560085')")
	update_query:="UPDATE employee_details SET name = ?, age = ?, address = ? where id = ?"
	update, err := db.ExecContext(context.Background(), update_query, ename, eage, eaddr, id)
    if err != nil {
		fmt.Printf("Update unsuccessful: %q\n", err)
		return "Failed to update", err
	}
	rows, _ :=update.RowsAffected()
	if rows==0{
		return "Failed to update", fmt.Errorf("unsuccessful Update")
	}
	fmt.Printf("Updated id: %v\n", id)
	return "Successfully updated id : "+strconv.Itoa(int(id)), nil
}

func Mysql_db_delete_employee(id int64) (string, error){
	db, err := connections.Mysql_db_Connection()
    // if there is an error opening the connection, handle it
    if err != nil {
        // panic(err.Error())
		return "Failed to delete ", err
    }

	defer db.Close()
	// insert, err := db.Query("INSERT INTO employee_details(name, age, address) VALUES('Vishnu J G', 21, '42, 2nd main, Poorna Pragna Layout BSK 3rd stage, Bengaluru 560085')")
	delete_query:="DELETE FROM employee_details where id = ?"
	deleted, err := db.ExecContext(context.Background(), delete_query, id)
    if err != nil {
		fmt.Printf("Update unsuccessful: %q\n", err)
		return "Failed to delete", err
	}
	rows, _ :=deleted.RowsAffected()
	if rows==0{
		return "Failed to delete", fmt.Errorf("delete Unsuccessful")
	}
	fmt.Printf("Deleted id: %v\n", id)
	return "Successfully deleted employee with id : "+strconv.Itoa(int(id)), nil
}
