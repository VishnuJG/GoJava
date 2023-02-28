package Tests.employee;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;

@Document(collection="employee_details")
public class Employee {
     @Id
     public Integer id;

     public String name;

     public Integer age;

     public String address;


    public Employee() {}

     public Employee(Integer id , String name, Integer age, String address)
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


@Document(collection="employee_details")
class Employee2 {
     @Id
     public ObjectId _id;
     public Integer id;
     public String name;

     public Integer age;

     public String address;


    public Employee2() {}

     public Employee2(ObjectId _id , String name, Integer age, String address)
    {
  
        super();
  
        this._id = (ObjectId)_id;
  
        this.name = name;
  
        this.age = age;
  
        this.address = address;
  
           
    }
    
    public Employee2( Integer id, String name, Integer age, String address)
    {
  
        super();
  
        this.id = id;
  
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
    public ObjectId get_Id()
    {
  
         return _id;
    }
  
    public void set_Id(ObjectId _id)
    {
  
         this._id = _id;
    }

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

