package connections


import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
)

const (  
    username = "root"
    password = "Vishnu@2001"
    hostname = "127.0.0.1:3306"
    dbname   = "employeedb"
)

func dsn() string {  
    return fmt.Sprintf("%s:%s@tcp(%s)/%s", username, password, hostname, dbname)
}

func Mysql_db_Connection() (*sql.DB, error) {  
    db, err := sql.Open("mysql", dsn())
    if err != nil {
        fmt.Printf("Error %s when opening DB\n", err)
        return nil, err
    }
    return db, nil
}