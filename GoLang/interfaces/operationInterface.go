package interfaces

import(
	
	"employee.info/m/entity"
	m "employee.info/m/services"
	
)




type EmployeeService interface {
	// function to get details of a particular employee based on the employee's id
	DbReadEmployeeDetails(id int) (entity.Employee, error)

	// function to get details of all the employees
	DbReadAllEmployeeDetails() []entity.Employee

	// function to insert a new employee details into the database
	DbInsertNewEmployee(employee entity.Employee) (string, error)

	// function to update the details of an employee
	DbUpdateEmployeeDetails(id int, name string, age int, address string) (string, error)

	// function to delete details of a particular employee
	DbDeleteEmployeeDetails(id int) (string, error)
}


// Mongo implementation of the interface
type MongoEmployeeServiceImpl struct{}

func (e *MongoEmployeeServiceImpl) DbReadEmployeeDetails(id int) (entity.Employee, error) {
	return m.Mongo_db_read_employee_details(id)
}

func (e *MongoEmployeeServiceImpl) DbReadAllEmployeeDetails() ([]entity.Employee, error) {
	return m.Mongo_db_read_all_employee_details()
}

func (e *MongoEmployeeServiceImpl) DbInsertNewEmployee(employee entity.Employee) (string, error) {
	return m.Mongo_db_insert_new_employee(employee)
}

func (e *MongoEmployeeServiceImpl) DbUpdateEmployeeDetails(id int, name string, age int, address string) (string, error) {
	return m.Mongo_db_update_employee_details(id, name, age, address)
}

func (e *MongoEmployeeServiceImpl) DbDeleteEmployeeDetails(id int) (string, error) {
	return m.Mongo_db_delete_employee_details(id)
}


// MySQL implementation of the interface
type MysqlEmployeeServiceImpl struct{}

func (e *MysqlEmployeeServiceImpl) DbReadEmployeeDetails(id int) (entity.Employee, error) {
	return m.Mysql_db_get_employee(id)
}

func (e *MysqlEmployeeServiceImpl) DbReadAllEmployeeDetails() ([]entity.Employee, error) {
	return m.Mysql_db_get_all_employee()
}

func (e *MysqlEmployeeServiceImpl) DbInsertNewEmployee(employee entity.Employee) (string, error) {
	return m.Mysql_db_insert(employee.Name, employee.Age, employee.Address)
}

func (e *MysqlEmployeeServiceImpl) DbUpdateEmployeeDetails(id int, name string, age int, address string) (string, error) {
	return m.Mysql_db_update_employee_details(int64(id), name, age, address)
}

func (e *MysqlEmployeeServiceImpl) DbDeleteEmployeeDetails(id int) (string, error) {
	return m.Mysql_db_delete_employee(int64(id))
}