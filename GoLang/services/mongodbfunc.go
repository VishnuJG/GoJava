package services

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"strconv"
	"errors"
	"employee.info/m/entity"
	"employee.info/m/connections"
)

// type Employee struct {
//     ID   int    `json:"id"`
//     Name string `json:"name"`
// 	Age int		`json:"age"`
// 	Address string `json:"name"`
// }

// var collection *mongo.Collection
var ctx = context.TODO()


// Function to insert a new employee record into the employee table
func Mongo_db_insert_new_employee(employee entity.Employee)(string, error){
	allemp, _:= Mongo_db_read_all_employee_details()
	
	maxId := 0
    for _, emp := range allemp {
        if emp.ID > maxId {
            maxId = emp.ID
        }
    }
	employee.ID=maxId+1
	collection, err := connections.Mongo_db_Connection()
	if err != nil{
		fmt.Println(err)
	}
	_, err = collection.InsertOne(context.TODO(), employee)
	if err != nil{
		fmt.Println(err)
		return "Unsuccessful insert", err
	}
	return "Successful insert new employee with id : "+strconv.Itoa(maxId+1), nil
}

// Function to get details of a particular employee based on the id provided
func Mongo_db_read_employee_details(id int)(entity.Employee, error){
	collection, err := connections.Mongo_db_Connection()
	if err != nil{
		fmt.Println(err)
	}
	filter := bson.D{{"id", id}}
	var emp entity.Employee
	err = collection.FindOne(context.TODO(), filter).Decode(&emp)
	if err != nil{
		fmt.Println(err)
		return emp, err
	}else{
		return emp, nil
	}
}


// Function to get details of all the employees in the database
func Mongo_db_read_all_employee_details()([]entity.Employee, error){
	var emp []entity.Employee
	collection, err := connections.Mongo_db_Connection()
	if err != nil{
		fmt.Println(err)
		return emp, err
	}
	filter := bson.D{}
	
	cur, _ := collection.Find(context.TODO(), filter)
	if err = cur.All(context.TODO(), &emp); err != nil {
        fmt.Println(err)
	}
	return emp, nil
}


// Function to update the details of an employee
func Mongo_db_update_employee_details(id int, name string, age int, address string)(string, error){
	collection, err := connections.Mongo_db_Connection()
	if err != nil{
		fmt.Println(err)
	}
	res, err := collection.UpdateOne(ctx,bson.M{"id": id},bson.D{{"$set", bson.D{{"name", name}}},{"$set", bson.D{{"age", age}}},{"$set", bson.D{{"address", address}}}},)
	if err != nil {
		fmt.Println(err)
		return "Unsuccessful update", err
	}
	if res.ModifiedCount==0{
		return "Unsuccessful update: document not found or is up to date", errors.New("document not Updated")
	}
	fmt.Printf("Updated %v Documents!\n", res.ModifiedCount)
	return "Successfully updated", nil
}


// Function to delete an employee's details
func Mongo_db_delete_employee_details(id int)(string, error){
	collection, err := connections.Mongo_db_Connection()
	if err != nil{
		fmt.Println(err)
	}
	res, err := collection.DeleteOne(context.TODO(), bson.D{{"id", id}})
	if err != nil {
		fmt.Println(err)
		return "Unsuccessful delete", err
	}
	if res.DeletedCount==0{
		return "Unsuccessful delete: document not found", errors.New("document not found")
	}
	fmt.Println(res)
	return "Successful delete", nil
}
