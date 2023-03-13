
# MySQL Database Setup
database name : employeedb
table name : employee_details
+---------+--------------+------+-----+---------+----------------+
| Field   | Type         | Null | Key | Default | Extra          |
+---------+--------------+------+-----+---------+----------------+
| id      | int unsigned | NO   | PRI | NULL    | auto_increment |
| name    | varchar(150) | NO   | UNI | NULL    |                |
| age     | int          | YES  |     | NULL    |                |
| address | longtext     | YES  |     | NULL    |                |
+---------+--------------+------+-----+---------+----------------+

## APIs

1.Get all employees in a particular database or all the databases
```
GET 
Java : localhost:8081/?db=< | mongo | mysql >
Go : localhost:10000/?db=< | mongo | mysql >
```
<br/>
2. Get a particular employee's details based on his id
```
GET
Java : localhost:8081/employee/{id}?db=< | mongo | mysql >
Go : localhost:10000/employee/{id}?db=< | mongo | mysql >
```
<br/>
3. Insert a new employee's details (have the employee details in the body of the request)
```
POST
Java : localhost:8081/employee?db=< | mongo | mysql >
Go : localhost:10000/employee?db=< | mongo | mysql >
```
<br/>
4. Update an employee's details (have the employee details in the body of the request)
```
PUT
Java : localhost:8081/employee/{id}?db=< | mongo | mysql >
Go : localhost:10000/employee/{id}?db=< | mongo | mysql >
```

<br/>
5. Delete an employee's details 
```
DELETE
Java : localhost:8081/employee/{id}?db=< | mongo | mysql >
Go : localhost:10000/employee/{id}?db=< | mongo | mysql >
```

