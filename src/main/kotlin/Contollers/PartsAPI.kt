package Contollers

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
}