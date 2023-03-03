package main
import(
	"net/http"
	"log"
	"github.com/gorilla/mux"
	"employee.info/m/controller"
)

func handleRequests() {
    myRouter := mux.NewRouter()
    myRouter.HandleFunc("/", controller.All_employees).Methods("GET")
	myRouter.HandleFunc("/employee/{id}", controller.Unique_employee_details).Methods("GET")
	myRouter.HandleFunc("/employee", controller.Insert_employee_details).Methods("POST") // insert
	myRouter.HandleFunc("/employee/{id}", controller.Delete_employee_details).Methods("DELETE")
	myRouter.HandleFunc("/employee/{id}", controller.Db_update_employee_details).Methods("PUT") // update
    log.Fatal(http.ListenAndServe(":10000", myRouter))
}

func main() {
	handleRequests()

}