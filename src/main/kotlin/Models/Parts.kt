package Models

data class Parts(
    val partName: String,
    val partNumber: String,
    val manufacturer: String,
    val price: Double
) {
    override fun toString(): String {
        return """
       
        Part Name:    $partName
        Part Number:  $partNumber
        Manufacturer: $manufacturer
        Price:        $price
       -----------------------------
    """.trimIndent()
    }

}

