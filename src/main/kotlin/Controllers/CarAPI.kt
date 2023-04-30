package Controllers

import Models.Car
import Models.Parts

import persistence.Serializer


class CarAPI(serializerType: Serializer){
    private fun formatListString(carsToFormat: List<Car>): String =
        carsToFormat
            .joinToString(separator = "\n") { car ->
                carList.indexOf(car).toString() + ": " + car.toString()
            }

     var carList = ArrayList<Car>()
    private var serializer: Serializer = serializerType
    fun add(car: Car): Boolean {
        return carList.add(car)
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return try {
            (index >= 0 && index < list.size)
        } catch (e: IndexOutOfBoundsException) {
            println("Invalid index. ${e.message}")
            false
        }
    }


    fun removeCar(indexToDelete: Int): Car? {
        return if (isValidListIndex(indexToDelete, carList)) {
            carList.removeAt(indexToDelete)
        } else {
            println("Invalid car index.")
            null
        }
    }

    fun numberOfCars(): Int {
        return carList.size
    }

    fun listCarAndParts(): String =
        if  (carList.isEmpty()) "No Cars stored in the system"
        else formatListString(carList)

    /**

    This function returns a list of strings containing details of all the cars stored in the system.

    If the carList is empty, the function returns a list containing the string "No Cars stored in the system".

    The function uses the mapIndexed() function to iterate over the carList and return a formatted string for each car.

    The formatted string includes the car's index, make, model, year, color, price, and whether the car is repaired or not.

    The trimIndent() function is called to remove any leading whitespace from the formatted string.

    The formatted string is then added to a list and returned by the function.
     */
    fun listAllCars(): List<String> {
        return if (carList.isEmpty()) {
            listOf("No Cars stored in the system")
        } else {
            carList.mapIndexed { index, car ->
                """
         
            Index: ${index} 
            Make: ${car.make}
            Model: ${car.model}
            Year: ${car.year}
            Color: ${car.colour}
            Price: ${car.price}
            Car Repaired: ${if (car.isCarRepaired) "Yes" else "No"}
            -------------------------------------
            """.trimIndent()
            }
        }
    }
    /**
    This function adds a given part to the parts list of a car identified by the carIndex.

    The function first checks if the carIndex is valid by calling the isValidListIndex() function, which checks if the carIndex is within the bounds of the car list. If the index is invalid, the function prints out an error message and returns.

    If the index is valid, the function retrieves the car object at the index from the carList, and adds the part to the car's parts list. Finally, the function prints out a success message to indicate that the part has been added successfully.
    */
    fun addPartToCar(carIndex: Int, part: Parts) {
        // Check if the car index is within the bounds of the car list
        try {
            if (!isValidListIndex(carIndex, carList)) {
                println("Invalid car index.")
                return
            }
        } catch (e: NumberFormatException) {
            println("Invalid input. ${e.message}")
            return
        }

        // Add the part to the car's parts list
        val car = carList[carIndex]
        car.parts.add(part)
        println("Part added to car successfully.")
    }


    /**

    Searches for cars in the car list that match either the make or the model provided.

    @param make the make of the car to search for

    @param model the model of the car to search for

    @return a list of Car objects that match the provided make or model
     */
    fun searchMakeOrModel(make: String, model: String): List<Car> {
// create an empty list to store the matching cars
        val matchingCars = mutableListOf<Car>()

// iterate through the car list and check if each car's make or model matches the provided values
        for (car in carList) {
            if (car.make == make || car.model == model) {
                matchingCars.add(car)
            }
        }

// if no matching cars were found, print a message to the console
        if (matchingCars.isEmpty()) {
            println("No cars found for make=$make and model=$model.")
        }

// return the list of matching cars
        return matchingCars
    }

    fun searchCarsByYear(year: Int): List<Car> {
        return carList.filter { it.year == year }
    }

    /**

 This function takes a string 'colour' as input and returns a list of cars whose 'colour' property matches the input.
 The function uses the built-in 'filter' function to filter the 'carList' list by comparing each car's 'colour' property to the input.
 The filter function returns a new list containing only the cars that match the condition.
 The function then returns this filtered list of cars.
 */
    fun searchByColour(colour: String): List<Car> {
        return carList.filter { it.colour==colour }
    }

    /**

    Updates the car information at the given index in the carList.

    @param indexToUpdate the index of the car to update

    @param newCarInfo the updated Car object containing new information

    @return true if the update was successful, false otherwise
     */
    fun updateCar(indexToUpdate: Int, newCarInfo: Car): Boolean {
// Check if the index is within the bounds of the car list
        if (!isValidListIndex(indexToUpdate, carList)) {
            println("Invalid car index.")
            return false
        }

// Update the car information at the given index
        carList[indexToUpdate] = newCarInfo
        return true
    }
    fun getCar(index: Int): Car? {
        return if (isValidListIndex(index, carList)) {
            carList[index]
        } else {
            println("Invalid part index.")
            null
        }
    }
    @Throws(Exception::class)
    fun loadCars() {
        carList = serializer.read() as ArrayList<Car>
    }

    @Throws(Exception::class)
    fun storeCars() {
        serializer.write(carList)
    }
}

