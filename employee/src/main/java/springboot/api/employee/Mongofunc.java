package GoJava.employee.src.main.java.springboot.api.employee;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.sql.*;
import com.mongodb.MongoClient; 
import com.mongodb.client.MongoDatabase; 
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.MongoCredential; 

public class Mongofunc {
    public String db_get_all_employee_details(){
        // MongoClientSettings settings = MongoClientSettings.builder()
        // .applyToConnectionPoolSettings(builder -> builder.maxWaitQueueSize(0))
        // .build();
        // MongoClient mongo = MongoClients.create(settings);
     
        // // Creating Credentials 
        // MongoCredential credential; 
        // credential = MongoCredential.createCredential("root", "employeedb", "Vishnu@2001".toCharArray()); 
        // System.out.println("Connected to the database successfully");  
        
        // // Accessing the database 
        // MongoDatabase database = mongo.getDatabase("employeedb"); 
        // System.out.println("Credentials ::"+ credential); 
        return "Hello World";
    }
    public static void main( String args[] ) {  
      
        // Creating a Mongo client 
        // MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
     
        // // Creating Credentials 
        // MongoCredential credential; 
        // credential = MongoCredential.createCredential("root", "employeedb", "Vishnu@2001".toCharArray()); 
        // System.out.println("Connected to the database successfully");  
        
        // // Accessing the database 
        // MongoDatabase database = mongo.getDatabase("employeedb"); 
        // System.out.println("Credentials ::"+ credential);     
     } 
}
