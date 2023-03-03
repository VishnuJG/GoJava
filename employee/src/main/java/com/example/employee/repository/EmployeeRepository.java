package com.example.employee.repository;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.employee.entity.EmployeeMySQL;
import com.example.employee.entity.EmployeeMongo;

public interface EmployeeRepository extends MongoRepository<EmployeeMongo, ObjectId> {
    EmployeeMySQL findById(int id);
    void deleteById(int id);
    boolean existsById(int id);
}
