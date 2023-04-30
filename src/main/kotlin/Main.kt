
import Models.Car
import Models.Parts
import Controllers.CarAPI
import Controllers.PartsAPI
import persistence.XMLSerializer
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readYesNo
import java.io.File

private val carAPI = CarAPI(XMLSerializer(File("cars.xml")))
private val partsAPI = PartsAPI(XMLSerializer(File("parts")))


// This is the main function which is the entry point of the program
fun main(args: Array<String>) {
    // This function runs the main menu of the application
    runMenu()
}

// This function displays the main menu options and returns the user's choice
/**

This function displays the main menu to the user and reads an integer input from them
@return An integer value representing the user's menu choice
 */
fun mainMenu() : Int {
// Display the main menu to the user and read their input
    return readNextInt("""
> ----------------------------------
> | GARAGE APP |
> ----------------------------------
> | MENU |
> | 1) Add Car or Part |
> | 2) List Car or Part |
> | 3) Update Car or Part |
> | 4) Remove Car or Part |
> | 5) Search Cars Or Parts |
> | 6) Calculate price of parts |
> | 21) Save |
> | 22) Load |
> ----------------------------------
> | 0) Exit |
> ----------------------------------
> ==>> """.trimMargin(">"))
}
/*
This function runs the main menu of the application. It displays the main menu options repeatedly until the user chooses to exit the application.
*/
fun runMenu() {
// This loop displays the main menu options repeatedly until the user chooses to exit the application
    do {
// Display the main menu options and read the user's choice
        val option = mainMenu()
// Execute the corresponding function based on the user's choice
        when (option) {
            1 -> add()
            2 -> list()
            3 -> update()
            4 -> remove()
// If the user chooses to search for cars, display the search menu and read the user's choice
            5 -> searchMenu()
            6 -> calculateTotalCost()
            21 -> save()
            22 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

/**
This function lists all the cars in the garage by calling the listAllCars() function of the carAPI object.
The carAPI object is an instance of a class that manages the cars in the garage, and the listAllCars() function returns a formatted string of all the cars in the garage. The function then prints this string to the console.
*/

fun listCars() {
    println( carAPI.listAllCars())
}

/**

This function retrieves and displays all parts stored in the parts database through the partsAPI.
It uses the partsAPI's listAllParts() method to retrieve all the parts and then prints them to the console.
If there are no parts in the database, an empty list is returned and displayed to the console.
 */
fun listParts() {
    println(partsAPI.listAllParts())
}

/**
This function allows the user to add a new car or a new part or add a part to an existing car. It displays the add menu options repeatedly until the user chooses to return to the main menu.

The function uses a while loop to continue displaying the menu until the user chooses to return to the main menu. The user's choice is read using readNextInt function and the corresponding function is executed using a when expression.

The options are:

Add new car: Calls addCar() function to add a new car.
Add new part: Calls addPart() function to add a new part.
Add part to existing car: Calls addPartToCar() function to add a part to an existing car.
Return to Main Menu: Returns to the main menu.
If the user enters an invalid choice, it prints an error message and displays the menu again.
*/
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

/**

This function adds a new car to the database. It first prompts the user to enter the make, model, year, color and price of the car.

If the car is repaired, it also prompts the user to add parts for the car. After adding the car and parts (if applicable) to the database,

it prints a success message to the console. If the car cannot be added to the database, it prints an error message to the console.
 */
fun addCar() {
// Prompt the user to enter the make, model, year, color and price of the car
    val make = readNextLine("Enter the make of the car: ")
    val model = readNextLine("Enter the model of the car: ")
    val year = readNextInt("Enter the year of the car: ")
    val color = readNextLine("Enter the color of the car: ")
    val price = readNextDouble("Enter the price of the car: ")

// Prompt the user to indicate if the car is repaired or not
    val isRepaired = readYesNo("Is the car repaired? (y/n): ")

// If the car is repaired, prompt the user to add parts for the car
    if (isRepaired == true) {
        println("Adding parts for ${make} ${model} (${year})")
        val partsList = mutableListOf<Parts>()
        val partName = readNextLine("Enter part name: ")
        val partNumber = readNextLine("Enter Part number: ")
        val manufacturer = readNextLine("Enter manufacturer: ")
        val price = readNextDouble("Enter price: ")
        val part = Parts(partName, partNumber, manufacturer, price)
        partsList.add(part)
        partsAPI.add(part)
        val isAdded = carAPI.add(Car(make, model, year, color, price, isRepaired, partsList))
        if (isAdded) {
            println("Car and part added successfully!")
        } else {
            println("Unable to add car and part")
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
/**

This function allows the user to add a part to the database.
It reads the part name, part number, manufacturer, and price from the user using the readNextLine and readNextDouble functions.
Then it creates a new Parts object with the information obtained from the user and calls the add method of the partsAPI object to add the part to the database.
If the part is added successfully, it prints a success message. Otherwise, it prints an error message.
 */
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
        val menu = """
        > ----------------------------------
        > |         GARAGE APP             |
        > ----------------------------------
        > |           LIST MENU            |
        > |   1) List all cars             |
        > |   2) List all parts            |
        > |   3) List cars with parts      |
        > ----------------------------------
        > |   0) Return to Main Menu       |
        > ----------------------------------
    """.trimIndent()

        println(menu)

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




/**

This function allows the user to add a part to a car.

It first displays a list of all available cars and prompts the user to select a car by index.

Then, it displays a list of all available parts and prompts the user to select a part by index.

Finally, it adds the selected part to the selected car.

If an invalid car or part index is entered, an error message is displayed and the function exits.
 */
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
    println(partsAPI.listAllParts())

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


/**

This function allows the user to search for cars based on their make and model.
It first prompts the user to enter the make and model of the car they are looking for.
Then, it calls the searchMakeOrModel() method of the carAPI object to search for cars
with the matching make and model.
If no matching cars are found, it displays a message saying so.
If matching cars are found, it displays the number of cars found and then loops through
the list of matching cars and displays their details.
 */
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
        val menu = """
        > ----------------------------------
        > |         GARAGE APP             |
        > ----------------------------------
        > |        SEARCH MENU             |
        > |   1) Search for a car          |
        > |   2) Search for a part         |
        > ----------------------------------
        > |   0) Return to Main Menu       |
        > ----------------------------------
    """.trimIndent()

        choice = readNextInt("Enter your choice: ")

        when (choice) {
            1 -> searchCar()
            2 -> searchParts()
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
            > ----------------------------------
            > |   0) Return to Main Menu       |
            > ----------------------------------
            > ==>> """.trimMargin(">")

        choice = readNextInt(searchCar)

        when (choice) {
            1 -> searchMakeModel()
            2 -> searchByYear()
            3 -> searchByColour()
            0 -> println("Returning to main menu...")
            else -> println("Invalid choice. Please enter a number between 1 and 3")
        }
    } while (choice != 0)
}

/**

This function allows the user to search for cars by colour.
It prompts the user to enter a colour to search for, and then uses the carAPI to search for cars with that colour.
If any cars with the specified colour are found, it displays a message with the number of cars found and their details.
If no cars with the specified colour are found, it displays a message indicating that no cars were found.
 */
fun searchByColour() {
    val colour = readNextLine("Enter a colour to search for: ")
    val carsByColour = carAPI.searchByColour(colour)
    if (carsByColour.isNotEmpty()) {
        println("Found ${carsByColour.size} car(s) with $colour colour:")
        carsByColour.forEach { println(it) }
    } else {
        println("No cars found with $colour colour.")
    }
}

/**

This function allows the user to search for cars by year.

It first prompts the user to enter the year of the car to search for.

If the entered year is invalid, it displays an error message and exits the function.

Then, it searches for cars with matching year using the carAPI.

If no cars are found with matching year, it displays a message indicating no cars were found and exits the function.

If matching cars are found, it displays the number of cars found and a list of the cars.
*/
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

/**

This function displays a menu for searching parts.

The user can choose to search for parts by name or number, or return to the main menu.

If the user chooses to search for parts, they will be prompted to enter a search query.

The function then displays any matching parts found.

If no results are found, a message is displayed indicating such.

The function will loop until the user chooses to return to the main menu.
 */
fun searchParts() {
    var choice: Int

    do {
        println(
            """
            > ----------------------------------
            > |       SEARCH PARTS MENU        |
            > ----------------------------------
            > |   1) Search by name or number  |
            > ----------------------------------
            > |   0) Return to Main Menu       |
            > ----------------------------------
            """.trimIndent()
        )

        choice = readNextInt("Enter your choice: ")

        when (choice) {
            1 -> {
                val query = readNextLine("Enter the name or part number to search for: ")
                val results = partsAPI.searchByNameOrNumber(query)
                if (results.isNotEmpty()) {
                    println("Results:")
                    results.forEach { println(it) }
                } else {
                    println("No results found.")
                }
            }

            0 -> println("Returning to main menu...")
            else -> println("Invalid choice. Please try again.")
        }
    } while (choice != 0)
}

/**

This function lists all cars and their associated parts.
It simply calls the carAPI.listCarAndParts() function and displays the results.
 */
    fun listCarsWithParts() {
        println(carAPI.listCarAndParts())
    }
fun update() {
    val menu = """
        > ----------------------------------
        > |           UPDATE MENU           |
        > ----------------------------------
        > |       1. Update Car             |
        > |       2. Update Part            |
        > |       0. Back to Main Menu      |
        > ----------------------------------
        """.trimIndent()

    while (true) {
        println(menu)

        when (readNextInt("Enter your choice: ")) {
            1 -> updateCar()
            2 -> updatePart()
            0 -> return println("Returning to main menu...")
            else -> println("Invalid option. Please try again.")
        }
    }
}



/**

This function updates the information of a car.

It first displays a list of all available cars and prompts the user to select a car by index.

It then prompts the user to enter new values for the make, model, year, colour, and price of the car.

If the user leaves any field blank, the old value will be kept.

The function then updates the selected car with the new information.

If an invalid car index is entered, an error message is displayed and the function exits.
 */
fun updateCar() {
// Display list of all cars and prompt user to select one by index
    println(carAPI.listAllCars())
    val carIndex = readNextInt("Enter the index of the car to update:")

// Get the car to update and check if it is valid
    val carToUpdate = carAPI.getCar(carIndex)
    if (carToUpdate == null) {
        println("Invalid car index.")
        return
    }

// Prompt user for new values for the car's properties
    val newMake = readNextLine("Enter the new make (leave blank to keep '${carToUpdate.make}'): ")
    val newModel = readNextLine("Enter the new model (leave blank to keep '${carToUpdate.model}'): ")
    val newYear = readNextInt("Enter the new year (leave blank to keep '${carToUpdate.year}'): ")
    val newColor = readNextLine("Enter the new colour (leave blank to keep '${carToUpdate.colour}'): ")
    val newPrice = readNextDouble("Enter the new price (leave blank to keep '${carToUpdate.price}'): ")

// Create a new Car object with the updated information
    val updatedCar = Car(
        if (newMake.isNotBlank()) newMake else carToUpdate.make,
        if (newModel.isNotBlank()) newModel else carToUpdate.model,
        if (newYear != null) newYear else carToUpdate.year,
        if (newColor.isNotBlank()) newColor else carToUpdate.colour,
        if (newPrice != null) newPrice else carToUpdate.price
    )

// Update the car with the new information
    carAPI.updateCar(carIndex, updatedCar)
    println("Car updated successfully.")
}

fun updatePart() {
    // Print a list of all the parts
    println(partsAPI.listAllParts())

    // Prompt the user to enter the index of the part to update
    val partIndex = readNextInt("Enter the index of the part to update:")

    // Retrieve the part to be updated using the `getPart` method
    val partToUpdate = partsAPI.getPart(partIndex)

    // If the part does not exist, print an error message and return
    if (partToUpdate == null) {
        println("Invalid part index.")
        return
    }

    // Prompt the user to enter the new values for the part's name, part number, manufacturer, and price
    val newName = readNextLine("Enter the new name (leave blank to keep '${partToUpdate.partName}'): ")
    val newNumber = readNextLine("Enter the new part number (leave blank to keep '${partToUpdate.partNumber}'): ")
    val newManufacturer = readNextLine("Enter the new manufacturer (leave blank to keep '${partToUpdate.manufacturer}'): ")
    val newPrice = readNextDouble("Enter the new price (leave blank to keep '${partToUpdate.price}'): ")

    // Create a new `Parts` object with the updated values for the part
    val updatedPart = Parts(
        if (newName.isEmpty()) partToUpdate.partName else newName,
        if (newNumber.isEmpty()) partToUpdate.partNumber else newNumber,
        if (newManufacturer.isEmpty()) partToUpdate.manufacturer else newManufacturer,
        if (newPrice == null) partToUpdate.price else newPrice
    )

    // Update the part in the `PartsAPI` using the `updatePart` method
    partsAPI.updatePart(partIndex, updatedPart)

    // Print a message indicating that the part was updated successfully
    println("Part updated successfully.")
}
/**

Calculates the total cost of parts for a given car.

Displays a list of all cars in the carAPI and prompts the user to enter the index of the desired car.

Retrieves the selected car from the carAPI. If the car does not exist, an error message is printed and the function returns.

Calculates the total cost of parts for the car by iterating through each part in the car's list of parts and adding its price to the running total.

Displays the total cost of parts for the car.
 */
fun calculateTotalCost() {
    println(carAPI.listAllCars())
    val carIndex = readNextInt("Enter the index of the car: ")

    val car = carAPI.getCar(carIndex)
    if (car == null) {
        println("Invalid car index.")
        return
    }

    val parts = car.parts
    var totalCost = 0.0
    for (part in parts) {
        totalCost += part.price
    }

    println("The total cost of parts for ${car.make} ${car.model} (${car.year}) is: $$totalCost")
}

fun save() {
    try {
        carAPI.storeCars()
        partsAPI.storeParts()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        carAPI.loadCars()
        partsAPI.loadParts()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}




