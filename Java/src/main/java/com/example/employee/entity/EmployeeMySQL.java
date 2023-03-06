package com.example.employee.entity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


@Document(collection="employee_details")
public class EmployeeMySQL {
     @Id
     public Integer id;

     public String name;

     public Integer age;

     public String address;


    public EmployeeMySQL() {}

     public EmployeeMySQL(Integer id , String name, Integer age, String address)
    {
  
        super();
  
        this.id = (int)id;
  
        this.name = name;
  
        this.age = age;
  
        this.address = address;
  
           
    }
    
     
  
    // Overriding the toString method
    // to find all the values
    @Override
   public String toString()
    {
  
        return "Employee [id="
            + String.valueOf(id) + ", name="
            + name + ", age="
            + String.valueOf(age) + ", address="
            + address + "]";
           
    }
  
    // Getters and setters of
    // the properties
    public Integer getId()
    {
  
         return id;
    }
  
    public void setId(Integer id)
    {
  
         this.id = id;
    }
  
    public String getName()
    {
  
         return name;
    }
  
    public void setName(String name)
    {
  
         this.name = name;
    }
  
    public int getAge()
    {
  
         return age;
    }
  
    public void setAge(int age)
    {
  
         this.age = age;
    }
  
    public String getAddress()
    {
  
         return address;
    }
  
    public void setAddress(String address)
    {
  
         this.address = address;
    }
    
}

