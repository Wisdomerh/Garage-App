package Contollers

import Models.Car

class CarAPI {
    private fun formatListString(notesToFormat : List<Car>) : String =
        notesToFormat
            .joinToString (separator = "\n") { note ->
                carList.indexOf(note).toString() + ": " + note.toString() }

    private var carList = ArrayList<Car>()

    fun add(car: Car):Boolean {
        return carList.add(car)
    }

}