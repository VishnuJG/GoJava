package interfaces

import(
	// "net/http"
	// "strconv"
	// "fmt"
	// "encoding/json"
	// "github.com/gorilla/mux"
	"employee.info/m/entity"
	m "employee.info/m/services"
	
)

// type DatabaseOperations interface{
// 	Mysql_db_health_check() error;
// 	Mysql_db_insert(ename string, eage int, eaddr string ) (int, error);
// 	Mysql_db_get_employee(id int)(entity.Employee, error);
// 	Mysql_db_get_all_employee()([]entity.Employee, error);
// 	Mysql_db_update_employee_details(id int64, ename string, eage int, eaddr string ) (int, error)
// 	Mysql_db_delete_employee(id int64) (int, error)
// }


type EmployeeService interface {
	DbInsertNewEmployee(employee entity.Employee) (string, error)
	DbReadEmployeeDetails(id int) (entity.Employee, error)
	DbReadAllEmployeeDetails() []entity.Employee
	DbUpdateEmployeeDetails(id int, name string, age int, address string) (string, error)
	DbDeleteEmployeeDetails(id int) (string, error)
}

type MongoEmployeeServiceImpl struct{}

func (e *MongoEmployeeServiceImpl) DbInsertNewEmployee(employee entity.Employee) (string, error) {
	return m.Mongo_db_insert_new_employee(employee)
}

func (e *MongoEmployeeServiceImpl) DbReadEmployeeDetails(id int) (entity.Employee, error) {
	return m.Mongo_db_read_employee_details(id)
}

func (e *MongoEmployeeServiceImpl) DbReadAllEmployeeDetails() ([]entity.Employee, error) {
	return m.Mongo_db_read_all_employee_details()
}

func (e *MongoEmployeeServiceImpl) DbUpdateEmployeeDetails(id int, name string, age int, address string) (string, error) {
	return m.Mongo_db_update_employee_details(id, name, age, address)
}

func (e *MongoEmployeeServiceImpl) DbDeleteEmployeeDetails(id int) (string, error) {
	return m.Mongo_db_delete_employee_details(id)
}


type MysqlEmployeeServiceImpl struct{}

func (e *MysqlEmployeeServiceImpl) DbInsertNewEmployee(employee entity.Employee) (string, error) {
	return m.Mysql_db_insert(employee.Name, employee.Age, employee.Address)
}

func (e *MysqlEmployeeServiceImpl) DbReadEmployeeDetails(id int) (entity.Employee, error) {
	return m.Mysql_db_get_employee(id)
}

func (e *MysqlEmployeeServiceImpl) DbReadAllEmployeeDetails() ([]entity.Employee, error) {
	return m.Mysql_db_get_all_employee()
}

func (e *MysqlEmployeeServiceImpl) DbUpdateEmployeeDetails(id int, name string, age int, address string) (string, error) {
	return m.Mysql_db_update_employee_details(int64(id), name, age, address)
}

func (e *MysqlEmployeeServiceImpl) DbDeleteEmployeeDetails(id int) (string, error) {
	return m.Mysql_db_delete_employee(int64(id))
}