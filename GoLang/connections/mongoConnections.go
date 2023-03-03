package connections

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"


)

var collection *mongo.Collection
var ctx = context.TODO()

func Mongo_db_Connection()(*mongo.Collection, error){
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017/")
	client, err := mongo.Connect(ctx, clientOptions)
	if err != nil {
		fmt.Println(err)
		return nil, err
	}

	err = client.Ping(ctx, nil)
	if err != nil {
		fmt.Println(err)
		return nil, err
	}

	collection = client.Database("employeedb").Collection("employee_details")
	// fmt.Println("Done")
	return collection, nil
}