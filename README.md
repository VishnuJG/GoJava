
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