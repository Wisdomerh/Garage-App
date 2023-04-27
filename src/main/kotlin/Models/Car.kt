package Models


data class Car(
    val make: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: Double,
    var isCarRepaired: Boolean = false,
    var parts: MutableList<Parts> = mutableListOf()
) {
    override fun toString(): String {
        val repaired = if (isCarRepaired) "Yes" else "No"

        return """
Make:         $make
Model:        $model
Year:         $year
Color:        $color
Price:        $price
Car Repaired: $repaired
---------------------------
        Parts: (${parts.size}):            ${parts.joinToString(separator = "\n")}
        
    """.trimIndent()
}

}


