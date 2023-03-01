package com.example.employee;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee2, ObjectId> {
    Employee findById(int id);
    void deleteById(int id);
    boolean existsById(int id);
}
