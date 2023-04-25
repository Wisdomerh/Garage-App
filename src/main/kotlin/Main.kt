
import Contollers.CarAPI
import Contollers.PartsAPI
import Models.Car
import Models.Parts
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readYesNo
import java.io.File

private val carAPI = CarAPI()
private val partsAPI = PartsAPI()
// This is the main function which is the entry point of the program
fun main(args: Array<String>) {
    // This function runs the main menu of the application
    runMenu()
}

// This function displays the main menu options and returns the user's choice
fun mainMenu() : Int {
    // This function reads an integer input from the user and displays the menu options
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |         GARAGE APP             |
         > ----------------------------------
         > |    MENU                        |
         > |   1) Add a car                 |
         > |   2) List all cars             |
         > |   3) Update a car              |
         > |   4) Remove a car              |
         > |   5) Search cars               |
         > |   6) Save garage               |
         > |   7) Load garage               |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

// This function displays the search menu options and returns the user's choice
fun searchMenu() : Int {
    // This function reads an integer input from the user and displays the search menu options
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |         GARAGE APP             |
         > ----------------------------------
         > |    SEARCH MENU                 |
         > |   1) Search by model           |
         > |   2) Search by make            |
         > |   3) Search by year            |
         > |   4) Search by color           |
         > |   5) Search by price           |
         > ----------------------------------
         > |   0) Return to Main Menu       |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

// This function runs the main menu of the application
fun runMenu() {
    // This loop displays the main menu options repeatedly until the user chooses to exit the application
    do {
        // Display the main menu options and read the user's choice
        val option = mainMenu()
        // Execute the corresponding function based on the user's choice
        when (option) {
            1  -> addCar()
            2  -> listCars()
           // 3  -> updateCar()
            4  -> removeCar()
            // If the user chooses to search for cars, display the search menu and read the user's choice
           // 5  -> searchMenu()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun listCars() {
    println( carAPI.listAllCars())
}

// This function adds a new car to the garage
fun addCar() {
    val make = readNextLine("Enter the make of the car: ")
    val model = readNextLine("Enter the model of the car: ")
    val year = readNextInt("Enter the year of the car: ")
    val color = readNextLine("Enter the color of the car: ")
    val price = readNextDouble("Enter the price of the car: ")
    val isRepaired = readYesNo("Is the car repaired? (y/n): ")
    if (isRepaired == true) {
        println("Adding parts for ${make} ${model} (${year})")

            val partName = readNextLine("Enter part name: ")
            val partNumber = readNextLine("Enter part number: ")
            val manufacturer = readNextLine("Enter manufacturer: ")
            val price = readNextDouble("Enter price: ")
            val part = partsAPI.add(Parts(partName, partNumber, manufacturer, price))
            val isAdded = carAPI.add(Car(make, model, year, color, price, isRepaired))
            if (isAdded) {
                println("Car added successfully!")
            } else {
                println("Unable to add car.")
            }
        }else if(isRepaired == false){
        val isAdded = carAPI.add(Car(make, model, year, color, price, isRepaired))


        if (isAdded) {
            println("Car added successfully!")
        } else {
            println("Unable to add car.")
        }
    }
    }


fun removeCar() {
    listCars()
    if (carAPI.numberOfCars() > 0) {
        //only ask the user to choose the car to remove if cars exist
        val indexToRemove = readNextInt("Enter the index of the car to remove: ")
        //pass the index of the car to CarAPI for removing and check for success.
        val carToRemove = carAPI.removeCar(indexToRemove)
        if (carToRemove != null) {
            println("Removal Successful! Removed car: ${carToRemove.make} ${carToRemove.model}")
        } else {
            println("Removal NOT Successful")
        }
    }
}

fun exitApp(){
    println("Exiting...bye")
    System.exit(0)
}