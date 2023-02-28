package GoJava.employee.src.main.java.springboot.api.employee; 
import GoJava.employee.src.main.java.springboot.api.employee.*; 
import org.bson.types.ObjectId;
// import com.example
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;
public interface EmployeeRepository extends MongoRepository<Employee2, ObjectId> {
    Employee findById(int id);
    void deleteById(int id);
}
