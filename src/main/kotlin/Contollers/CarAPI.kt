package Contollers

import Models.Car

class CarAPI {
    private fun formatListString(carsToFormat: List<Car>): String =
        carsToFormat
            .joinToString(separator = "\n") { car ->
                carList.indexOf(car).toString() + ": " + car.toString()
            }

    private var carList = ArrayList<Car>()

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

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, carList);
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
            carList.map {
                "Make: ${it.make}, Model: ${it.model}, Year: ${it.year}, Color: ${it.color}, Price: ${it.price}"
            }
        }
    }

}

