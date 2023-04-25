package Models


data class Car(
    val make: String,
    val model: String,
    val year: Int,
    val color: String,
    val price: Double
) {
    override fun toString(): String {
        return """
            Make:  $make
            Model: $model
            Year:  $year
            Color: $color
            Price: $price
        """.trimIndent()
    }
}
