package Controllers


import Models.Parts
import persistence.Serializer

class PartsAPI(serializerType: Serializer) {
    private fun formatListString(partsToFormat: List<Parts>): String =
        partsToFormat
            .joinToString(separator = "\n") { part ->
                partList.indexOf(part).toString() + ": " + part.toString()
            }
    var partList = ArrayList<Parts>()
    private var serializer: Serializer = serializerType

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

    /**

    Search for a part in the part list by its name or number.

    @param query The string to search for, which can be either the name or number of the part.

    @return A list of Parts objects that match the query.

    If no parts are found, an empty list is returned and a message is printed to the console.
     */
    fun searchByNameOrNumber(query: String): List<Parts> {
        val matchingParts = partList.filter { it.partName == query || it.partNumber == query }

        if (matchingParts.isEmpty()) {
            println("No parts found for query: $query")
        }

        return matchingParts
    }


    /**

    Updates a part in the part list with new information.

    @param indexToUpdate the index of the part to update

    @param newPartInfo the new information to update the part with

    @return true if the part was successfully updated, false otherwise
     */
    fun updatePart(indexToUpdate: Int, newPartInfo: Parts): Boolean {
// Check if the part index is within the bounds of the part list
        if (!isValidListIndex(indexToUpdate, partList)) {
            println("Invalid part index.")
            return false
        }

// Update the part with new information
        partList[indexToUpdate] = newPartInfo
        return true
    }
    @Throws(Exception::class)
    fun loadParts() {
        partList = serializer.read() as ArrayList<Parts>
    }

    @Throws(Exception::class)
    fun storeParts() {
        serializer.write(partList)
    }
}
