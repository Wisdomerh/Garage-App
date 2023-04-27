
import Models.Car
import Models.Parts
import Controllers.CarAPI
import Controllers.PartsAPI
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readYesNo

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
    return readNextInt(""" 
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
            5  -> searchMenu()
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
       val partsList = mutableListOf<Parts>()
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
        val isAdded = carAPI.add(Car(make,model, year, color, price, isRepaired))


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




    fun addPartToCar() {
        // Display list of cars
        println(carAPI.listAllCars())

        // Get the car index from the user
        val carIndex = readNextInt("Select a car by index to add part: ")
        if (carIndex == null || carIndex !in 0 until carAPI.carList.size) {
            println("Invalid car index.")
            return
        }

        // Display list of parts from the partsAPI instance passed in from CarAPI
        println( partsAPI.listAllParts())

        // Get the part index from the user
        val partIndex = readNextInt("Select a part by index to add to the car: ")
        if (partIndex == null || partIndex !in 0 until partsAPI.partList.size) {
            println("Invalid part index.")
            return
        }

        // Add the part to the car using the partsAPI instance passed in from CarAPI
        val part = partsAPI.getPart(partIndex)
        if (part == null) {
            println("Invalid part index.")
            return
        }
        carAPI.addPartToCar(carIndex, part)

    }

fun searchMakeModel(){
    // Get the make and model from the user
    val make = readNextLine("Enter the make of the car: ")
    val model = readNextLine("Enter the model of the car: ")

    // Search for cars with matching make and model
    val matchingCars = carAPI.searchMakeOrModel(make, model)

    // Display the results
    if (matchingCars.isEmpty()) {
        println("No cars found with make '$make' and model '$model'.")
    } else {
        println("Found ${matchingCars.size} car(s) with make '$make' and model '$model':")
        for (car in matchingCars) {
            println(car)
        }
    }

}

fun searchMenu() : Int {
    var choice: Int

    do {
        println("> ----------------------------------")
        println("> |         GARAGE APP             |")
        println("> ----------------------------------")
        println("> |        SEARCH MENU             |")
        println("> |   1) Search for a car          |")
        println("> |   2) Search for a part         |")
        println("> ----------------------------------")
        println("> |   0) Return to Main Menu       |")
        println("> ----------------------------------")

        choice = readNextInt("Enter your choice: ")

        when (choice) {
            1 -> searchCar()
            //2 -> searchParts()
            0 -> println("Returning to main menu...")
            else -> println("Invalid choice. Please try again.")
        }
    } while (choice != 0)

    return choice
}

// This function displays the search menu options and returns the user's choice
fun searchCar() {
    var choice: Int

    do {
        val searchCar = """
            > ----------------------------------
            > |         GARAGE APP             |
            > ----------------------------------
            > |    SEARCH CAR                  |
            > |   1) Search by make and model  |
            > |   2) Search by year            |
            > |   3) Search by color           |
            > |   4) Search by price           |
            > ----------------------------------
            > |   0) Return to Main Menu       |
            > ----------------------------------
            > ==>> """.trimMargin(">")

        choice = readNextInt(searchCar)

        when (choice) {
            1 -> searchMakeModel()  // Search by make and model
            2 -> searchByYear()     // Search by year
           // 3 -> searchByColor()    // Search by color
           // 4 -> searchByPrice()    // Search by price
           // 0 -> println("Returning to main menu...")
            else -> println("Invalid choice. Please enter a number between 1 and 4.")
        }
    } while (choice != 0)
}

fun searchByYear() {
    val year = readNextInt("Enter the year of the car to search for: ")

    if (year == null) {
        println("Invalid year. Please enter a valid year.")
        return
    }

    val matchingCars = carAPI.searchCarsByYear(year)

    if (matchingCars.isEmpty()) {
        println("No cars found matching the year $year.")
        return
    }

    println("Found ${matchingCars.size} car(s) matching the year $year:")
    for (car in matchingCars) {
        println(car)
    }
}


fun listCarsWithParts() {
    println( carAPI.listCarAndParts())
}
