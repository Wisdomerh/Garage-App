package Controllers

import Models.Car
import Models.Parts


class CarAPI() {
    private fun formatListString(carsToFormat: List<Car>): String =
        carsToFormat
            .joinToString(separator = "\n") { car ->
                carList.indexOf(car).toString() + ": " + car.toString()
            }

    var carList = ArrayList<Car>()

    fun add(car: Car): Boolean {
        return carList.add(car)
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun removeCar(indexToDelete: Int): Car? {
        return if (isValidListIndex(indexToDelete, carList)) {
            carList.removeAt(indexToDelete)
        } else null
    }

    fun isValidIndex(index: Int): Car? {
        return if (carList.isNotEmpty() && index in 0 until carList.size) carList[index] else null
    }


    fun numberOfCars(): Int {
        return carList.size
    }
    fun listCarAndParts(): String =
        if  (carList.isEmpty()) "No Cars stored in the system"
        else formatListString(carList)

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
    fun addPartToCar(carIndex: Int, part: Parts) {
        // Check if the car index is within the bounds of the car list
        if (!isValidListIndex(carIndex, carList)) {
            println("Invalid car index.")
            return
        }

        // Add the part to the car's parts list
        val car = carList[carIndex]
        car.parts.add(part)
        println("Part added to car successfully.")
    }
    fun searchMakeOrModel(make: String, model: String): List<Car> {
        val matchingCars = mutableListOf<Car>()

        for (car in carList) {
            if (car.make == make || car.model == model) {
                matchingCars.add(car)
            }
        }

        if (matchingCars.isEmpty()) {
            println("No cars found for make=$make and model=$model.")
        }

        return matchingCars
    }
    fun searchCarsByYear(year: Int): List<Car> {
        return carList.filter { it.year == year }
    }
    fun searchByColour(colour: String): List<Car> {
        return carList.filter { it.colour==colour }
    }


}

