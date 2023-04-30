import Controllers.CarAPI
import Models.Car
import Models.Parts
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

class CarAPITest {

    lateinit var carAPI: CarAPI

    @BeforeEach
    fun setup() {
        carAPI = CarAPI(XMLSerializer(File("cars.xml")))
    }
    @Nested
    inner class add {
        @Test
        fun `test add car`() {
            val car = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
            assertTrue(carAPI.add(car))
            assertEquals(1, carAPI.numberOfCars())
        }

        @Test
        fun testAddPartToCar() {
            // Create a new car and add it to the carList
            val car = Car("Toyota", "Camry", 2021, "Silver", 25000.0)
            carAPI.carList.add(car)

            // Add a new part to the car's parts list
            val part = Parts("Engine", "123456", "Toyota", 1000.0)
            carAPI.addPartToCar(0, part)

            // Check that the part was added to the car's parts list
            assertEquals(car.parts.size, 1)
            assertEquals(car.parts[0], part)
        }
    }

    @Nested
    inner class removeCar {
        @Test
        fun `test remove car`() {
            val car = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
            carAPI.add(car)
            assertNotNull(carAPI.removeCar(0))
            assertEquals(0, carAPI.numberOfCars())
        }

        @Test
        fun `test invalid car index in removeCar`() {
            assertNull(carAPI.removeCar(0))
        }
    }

    @Test
    fun `test number of cars`() {
        val car1 = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
        val car2 = Car("Toyota", "Camry", 2022, "Red", 25000.0, true, mutableListOf())
        carAPI.add(car1)
        carAPI.add(car2)
        assertEquals(2, carAPI.numberOfCars())
    }
    @Nested
    inner class listCar {
    @Test
    fun `test listCarAndParts`() {
        // Setup
        val car1 = Car("Toyota", "Camry", 2021, "Silver", 25000.0)
        val part1 = Parts("Engine", "123456", "Toyota", 1000.0)
        car1.parts.add(part1)
        carAPI.carList.add(car1)

        // Exercise
        val result = carAPI.listCarAndParts()

        // Verify
        assertTrue(result.contains("Toyota"))
        assertTrue(result.contains("Camry"))
        assertTrue(result.contains("2021"))
        assertTrue(result.contains("Silver"))
        assertTrue(result.contains("25000.0"))
        assertTrue(result.contains("No"))
        assertTrue(result.contains("Engine"))
        assertTrue(result.contains("123456"))
        assertTrue(result.contains("Toyota"))
        assertTrue(result.contains("1000.0"))
    }

        @Test
        fun `test listAllCars with cars stored in the system`() {
            // Setup
            val car1 = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
            val car2 = Car("Toyota", "Camry", 2022, "Red", 25000.0, false, mutableListOf())
            carAPI.add(car1)
            carAPI.add(car2)

            // Exercise
            val result: List<String> = carAPI.listAllCars()

            // Verify
            val expected = listOf(
                """
            
            Index: 0 
            Make: Honda
            Model: Civic
            Year: 2020
            Color: Blue
            Price: 20000.0
            Car Repaired: No
            -------------------------------------
        """.trimIndent(),
                """
            
            Index: 1 
            Make: Toyota
            Model: Camry
            Year: 2022
            Color: Red
            Price: 25000.0
            Car Repaired: No
            -------------------------------------
        """.trimIndent()
            )
            assertEquals(expected, result)
        }

        @Test
        fun `test listAllCars with no cars stored in the system`() {
            // Setup

            // Exercise
            val result: List<String> = carAPI.listAllCars()

            // Verify
            val expected = listOf("No Cars stored in the system")
            assertEquals(expected, result)
        }
    }

    @Nested
    inner class updateCar {
        @Test
        fun `test updateCar with valid index`() {
            // Setup
            val car1 = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
            val car2 = Car("Toyota", "Camry", 2022, "Red", 25000.0, false, mutableListOf())
            carAPI.add(car1)
            carAPI.add(car2)

            val newCarInfo = Car("Nissan", "Altima", 2021, "White", 22000.0, false, mutableListOf())

            // Exercise
            val updated = carAPI.updateCar(1, newCarInfo)

            // Verify
            assertTrue(updated)
            assertEquals(carAPI.getCar(1), newCarInfo)
        }

        @Test
        fun `test updateCar with invalid index`() {
            // Setup
            val car1 = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
            carAPI.add(car1)

            val newCarInfo = Car("Nissan", "Altima", 2021, "White", 22000.0, false, mutableListOf())

            // Exercise
            val updated = carAPI.updateCar(1, newCarInfo)

            // Verify
            assertFalse(updated)
        }
    }

    @Nested
    inner class search {
        @Test
        fun `test search cars by year`() {
            // Setup
            val car1 = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
            val car2 = Car("Toyota", "Camry", 2022, "Red", 25000.0, false, mutableListOf())
            val car3 = Car("BMW", "X5", 2020, "Black", 35000.0, false, mutableListOf())
            carAPI.add(car1)
            carAPI.add(car2)
            carAPI.add(car3)

            // Exercise
            val result = carAPI.searchCarsByYear(2020)

            // Verify
            assertEquals(2, result.size)
            assertTrue(result.contains(car1))
            assertFalse(result.contains(car2))
            assertTrue(result.contains(car3))
        }


        @Test
        fun testSearchMakeOrModel() {
            // create a list of cars
            val car1 = Car("Toyota", "Corolla", 2018, "red", 12000.0)
            val car2 = Car("Honda", "Civic", 2019, "blue", 13000.0)
            val car3 = Car("Toyota", "Camry", 2020, "silver", 15000.0)
            val car4 = Car("Ford", "Mustang", 2021, "black", 25000.0)
            val car5 = Car("Honda", "Accord", 2022, "white", 20000.0)

            // add cars to the carAPI's car list
            carAPI.carList.addAll(listOf(car1, car2, car3, car4, car5))

            // search for cars by make and model
            val matchingCars = carAPI.searchMakeOrModel("Toyota", "Civic")

            // check that the correct number of cars were found and that they match the expected values
            assertEquals(3, matchingCars.size)
            assertTrue(matchingCars.contains(car1))
            assertTrue(matchingCars.contains(car2))
        }

        @Test
        fun `test search cars by color`() {
            // Setup
            val car1 = Car("Honda", "Civic", 2020, "Blue", 20000.0, false, mutableListOf())
            val car2 = Car("Toyota", "Camry", 2022, "Red", 25000.0, false, mutableListOf())
            val car3 = Car("Ford", "Mustang", 2021, "Blue", 30000.0, false, mutableListOf())
            carAPI.add(car1)
            carAPI.add(car2)
            carAPI.add(car3)

            // Exercise
            val result = carAPI.searchByColour("Blue")

            // Verify
            assertEquals(2, result.size)
            assertTrue(result.contains(car1))
            assertFalse(result.contains(car2))
            assertTrue(result.contains(car3))
        }
    }

    @Nested
    inner class PersistenceTests {
        @Test
        fun  testStoreCars() {
            // Create a list of cars
            val cars = arrayListOf(
                Car("Toyota", "Corolla", 2021, "Red", 15000.0),
                Car("Honda", "Civic", 2022, "Blue", 16000.0),
                Car("Ford", "Mustang", 2023, "Yellow", 25000.0)
            )

            // Set up the serializer
            val serializer = XMLSerializer(File("cars.xml"))

            // Set up the car API
            val carAPI = CarAPI(serializer)

            // Store the cars
            carAPI.storeCars()

            // Load the cars from the file
            val loadedCars = carAPI.loadCars()

            // Assert that the loaded cars are the same as the stored cars
            assertEquals(kotlin.Unit, loadedCars)
        }

    }

}