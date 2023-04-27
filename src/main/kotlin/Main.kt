
import Contollers.CarAPI
import Contollers.PartsAPI
import Models.Car
import Models.Parts
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readYesNo

private val carAPI = CarAPI()
private val partsAPI = PartsAPI()
private val partsList = mutableListOf<Parts>()
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
         > |   2) List                      |
         > |   3) Update a car              |
         > |   4) Remove a car              |
         > |   5) Search cars               |
         > |   7) Save garage               |
         > |   8) Load garage               |
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
            1  -> add()
            2  -> list()
           // 3  -> updateCar()
            4  -> remove()
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

fun listParts() {
    println(partsAPI.listAllParts())
}

fun add() {
    while (true) {
        println(
            """
            > ----------------------------------
            > |           ADD MENU             |
            > ----------------------------------
            > |    1) Add new car              |
            > |    2) Add new part             |
            > |    3) Add part to existing car |
            > |    0) Return to Main Menu      |
            > ----------------------------------
            """.trimIndent()
        )

        when (readNextInt("Enter your choice: ")) {
            1 -> addCar()
            2 -> addPart()
            3 -> addPartToCar()
            0 -> return
            else -> println("Invalid choice. Please try again.")
        }
    }
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
        val part = Parts(partName, partNumber, manufacturer, price)
        partsList.add(part)
        partsAPI.add(part)
        val isAdded = carAPI.add(Car(make, model, year, color, price, isRepaired, partsList))
        if (isAdded) {
            println("Car added successfully!")
        } else {
            println("Unable to add car.")
        }
    } else if (isRepaired == false) {
        val isAdded = carAPI.add(Car(make, model, year, color, price, isRepaired))


        if (isAdded) {
            println("Car added successfully!")
        } else {
            println("Unable to add car.")
        }
    }
}
fun addPart() {
    val partName = readNextLine("Enter part name: ")
    val partNumber = readNextLine("Enter part number: ")
    val manufacturer = readNextLine("Enter manufacturer: ")
    val price = readNextDouble("Enter price: ")
    val part = Parts(partName, partNumber, manufacturer, price)
    val isAdded = partsAPI.add(part)
    if (isAdded) {
        println("Part added successfully!")
    } else {
        println("Unable to add part.")
    }
}

fun addPartToCar() {
    val car = carAPI.listAllCars().getOrNull(readNextInt("Enter the index of the car you want to add a part to: "))
    if (car == null) {
        println("> Unable to find car.")
        return
    }

    val part = partsAPI.listAllParts().getOrNull(readNextInt("Enter the index of the part you want to add to the car: "))
    car.Parts.add(part)
    if (part == null) {
        println("> Unable to find part.")
        return
    }

    println("> Part added to car successfully!")
}




fun remove() {
    var option: Int
    do {println("""
            > ----------------------------------
            > |         REMOVE MENU             |
            > ----------------------------------
            > |   1) Remove Car                |
            > |   2) Remove Part               |
            > |   0) Return to Main Menu       |
            > ----------------------------------
            > """.trimMargin(">"))

        option = readNextInt("Enter an option: ")
        when (option) {
            1 -> removeCar()
            2 -> removePart()
            0 -> println("Returning to Main Menu...")
            else -> println("Invalid option. Please try again.")
        }
    } while (option != 0)
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

fun removePart() {
    listParts()
    if (partsAPI.numberOfParts() > 0) {
        //only ask the user to choose the car to remove if parts exist
        val indexToRemove = readNextInt("Enter the index of the part to remove: ")
        //pass the index of the car to PartsAPI for removing and check for success.
        val partToRemove = partsAPI.removePart(indexToRemove)
        if (partToRemove != null) {
            println("Removal Successful! Removed part: ${partToRemove.partName} ${partToRemove.partNumber}")
        } else {
            println("Removal NOT Successful")
        }
    }
}

fun exitApp(){
    println("Exiting...bye")
    System.exit(0)
}
fun list() {
    var choice: Int

    do {
        println("> ----------------------------------")
        println("> |         GARAGE APP             |")
        println("> ----------------------------------")
        println("> |           LIST MENU            |")
        println("> |   1) List all cars             |")
        println("> |   2) List all parts            |")
        println("> |   3) List cars with parts      |")
        println("> ----------------------------------")
        println("> |   0) Return to Main Menu       |")
        println("> ----------------------------------")

        choice = readNextInt("Enter your choice: ")

        when (choice) {
            1 -> listCars()
            2 -> listParts()
            3 -> listCarsWithParts()
            0 -> println("Returning to main menu...")
            else -> println("Invalid choice. Please try again.")
        }
    } while (choice != 0)
}

fun listCarsWithParts() {
    println( carAPI.listCarAndParts())
}