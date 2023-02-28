package Tests.employee;
// import springboot.api.employee.*; 
import org.bson.types.ObjectId;
// import com.example
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;
public interface EmployeeRepository extends MongoRepository<Employee2, ObjectId> {
    Employee findById(int id);
    void deleteById(int id);
    boolean existsById(int id);
}
