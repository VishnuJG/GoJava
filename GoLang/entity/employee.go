package entity


type Employee struct {
    ID   int    `json:"id"`
    Name string `json:"name"`
	Age int		`json:"age"`
	Address string `json:"address"`
}