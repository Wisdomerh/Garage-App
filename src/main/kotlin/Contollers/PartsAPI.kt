package Contollers

import Models.Car
import Models.Parts

class PartsAPI {
    private fun formatListString(partsToFormat: List<Parts>): String =
        partsToFormat
            .joinToString(separator = "\n") { part ->
                partList.indexOf(part).toString() + ": " + part.toString()
            }

    private var partList = ArrayList<Parts>()

    fun add(part: Parts): Boolean {
        return partList.add(part)
    }
    fun listAllParts(): String =
        if (partList.isEmpty()) {
            "No cars stored in the system"
        } else {
            formatListString(partList)
        }
    fun removePart(indexToDelete: Int): Parts? {
        return if (isValidListIndex(indexToDelete, partList)) {
            partList.removeAt(indexToDelete)
        } else null
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
    fun numberOfParts(): Int {
        return partList.size
    }
}