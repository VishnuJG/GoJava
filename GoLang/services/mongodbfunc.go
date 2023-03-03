package services

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	// "go.mongodb.org/mongo-driver/bson/primitive" 
	"go.mongodb.org/mongo-driver/mongo"

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

var collection *mongo.Collection
var ctx = context.TODO()

// func Mongo_db_Connection()(*mongo.Collection, error){
// 	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017/")
// 	client, err := mongo.Connect(ctx, clientOptions)
// 	if err != nil {
// 		fmt.Println(err)
// 		return nil, err
// 	}

// 	err = client.Ping(ctx, nil)
// 	if err != nil {
// 		fmt.Println(err)
// 		return nil, err
// 	}

// 	collection = client.Database("employeedb").Collection("employee_details")
// 	// fmt.Println("Done")
// 	return collection, nil
// }

// func mongo_db_get_number_of_employees() (int, error){
// 	collection, err := Mongo_db_Connection()
// 	if err != nil{
// 		fmt.Println(err)
// 	}
// 	count, err := collection.CountDocuments(context.TODO(),bson.D{})
// 	if err != nil {
// 		panic(err)
// 		return -1, err
// 	}
// 	// fmt.Printf("Number of documents : %d\n", count)
// 	return int(count), nil
// }

func Mongo_db_insert_new_employee(employee entity.Employee)(string, error){
	allemp:= Mongo_db_read_all_employee_details()
	
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
	// fmt.Println(result.InsertedID)
	return "Successful insert", nil
}

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

func Mongo_db_read_all_employee_details()([]entity.Employee){
	collection, err := connections.Mongo_db_Connection()
	if err != nil{
		fmt.Println(err)
	}
	filter := bson.D{}
	var emp []entity.Employee
	cur, err := collection.Find(context.TODO(), filter)
	if err = cur.All(context.TODO(), &emp); err != nil {
        fmt.Println(err)
	}
	return emp
}

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


// func main() {
	
// 	collection, err := dbConnection()
// 	if err != nil{
// 		fmt.Println(err)
// 	}


// 	// Counting the number of documents
// 	count, err := db_get_number_of_employees(collection)


// 	// Inserting/Creating a new user
// 	employee := Employee{ID : int(count)+1, Name:"Vishnu J G", Age: 22, Address: "41, 2nd main, Poorna Pragna Layout, Banashankari 3rd stage, Bengaluru 560085."}
// 	result, err := db_insert_new_employee(collection, employee)
// 	if err != nil{
// 		fmt.Println(err)
// 	}
// 	fmt.Println(result)


// 	// Reading an employee's detials
// 	var emp Employee
// 	emp, err = db_read_employee_details(collection, int(3))
// 	if err != nil{
// 		fmt.Println(err)
// 	}else{
// 		fmt.Println(emp.ID)
// 		fmt.Println(emp.Name)
// 		fmt.Println(emp.Age)
// 		fmt.Println(emp.Address)
// 	}
	
// 	// Update an employee's details
// 	res, err := db_update_employee_details(collection,1, "Vishnu J G", 23, "new Bangalore")
// 	if err != nil {
// 		fmt.Println(err)
// 	}
// 	fmt.Println(res)

// 	// Delete an employee's details
// 	ress, err := db_delete_employee_details(collection, 0)
// 	if err != nil {
// 		fmt.Println(err)
// 	}
// 	fmt.Println(ress)


// 	fmt.Println(db_read_all_employee_details(collection))
// }
