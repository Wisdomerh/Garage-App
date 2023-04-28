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

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun removePart(indexToDelete: Int): Parts? {
        return if (isValidListIndex(indexToDelete, partList)) {
            partList.removeAt(indexToDelete)
        } else {
            println("Invalid part index.")
            null
        }
    }

    fun numberOfParts(): Int {
        return partList.size
    }

    fun isValidIndex(index: Int): Parts? {
        return if (partList.isNotEmpty() && index in 0 until partList.size) {
            partList[index]
        } else {
            println("Invalid part index.")
            null
        }
    }

    fun getPart(index: Int): Parts? {
        return if (isValidListIndex(index, partList)) {
            partList[index]
        } else {
            println("Invalid part index.")
            null
        }
    }

    fun listAllParts(): String {
        return if (partList.isEmpty()) {
            "No parts stored in the system"
        } else {
            formatListString(partList)
        }
    }

    fun searchByNameOrNumber(query: String): List<Parts> {
        val matchingParts = partList.filter { it.partName == query || it.partNumber == query }

        if (matchingParts.isEmpty()) {
            println("No parts found for query: $query")
        }

        return matchingParts
    }

    fun updatePart(indexToUpdate: Int, newPartInfo: Parts): Boolean  {
        if (!isValidListIndex(indexToUpdate, partList)) {
            println("Invalid car index.")
            return false
        }

        partList[indexToUpdate] = newPartInfo
        return true
    }
}
