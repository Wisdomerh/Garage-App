package Controllers

import Models.Parts

class PartsAPI {
    private fun formatListString(partsToFormat: List<Parts>): String =
        partsToFormat
            .joinToString(separator = "\n") { part ->
                partList.indexOf(part).toString() + ": " + part.toString()
            }
 var partList = ArrayList<Parts>()

    fun add(part: Parts): Boolean {
        return partList.add(part)
    }


    fun listAllParts(): String =
        if (partList.isEmpty()) {
            "No parts stored in the system"
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
    fun isValidIndex(index: Int): Parts? {
        return if (partList.isNotEmpty() && index in 0 until partList.size) partList[index] else null
    }
    fun getPart(index: Int): Parts? {
        // Check if the index is within the bounds of the parts list
        if (index < 0 || index >= partList.size) {
            println("Invalid part index.")
            return null
        }
        // Return the part object corresponding to the given index
        return partList[index]
    }

}

