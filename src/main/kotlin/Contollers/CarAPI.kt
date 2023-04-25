package Contollers

import Models.Car

class CarAPI {
    private fun formatListString(notesToFormat: List<Car>): String =
        notesToFormat
            .joinToString(separator = "\n") { note ->
                carList.indexOf(note).toString() + ": " + note.toString()
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
}