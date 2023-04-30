package Controllers

import Controllers.PartsAPI
import Models.Parts
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

class PartsAPITest {

    lateinit var partsAPI: PartsAPI

    @BeforeEach
    fun setup() {
        partsAPI = PartsAPI(XMLSerializer(File("parts.xml")))
    }

    @Nested
    inner class add {
        @Test
        fun `test add part`() {
            val part = Parts("Engine", "123456", "Toyota", 1000.0)
            assertTrue(partsAPI.add(part))
            assertEquals(1, partsAPI.numberOfParts())
        }
    }

    @Nested
    inner class removePart {
        @Test
        fun `test remove part`() {
            val part = Parts("Engine", "123456", "Toyota", 1000.0)
            partsAPI.add(part)
            assertNotNull(partsAPI.removePart(0))
            assertEquals(0, partsAPI.numberOfParts())
        }

        @Test
        fun `test invalid part index in removePart`() {
            assertNull(partsAPI.removePart(0))
        }
    }

    @Test
    fun `test number of parts`() {
        val part1 = Parts("Engine", "123456", "Toyota", 1000.0)
        val part2 = Parts("Battery", "789012", "Honda", 500.0)
        partsAPI.add(part1)
        partsAPI.add(part2)
        assertEquals(2, partsAPI.numberOfParts())
    }

    @Nested
    inner class getPart {
        @Test
        fun `test get part`() {
            val part1 = Parts("Engine", "123456", "Toyota", 1000.0)
            val part2 = Parts("Battery", "789012", "Honda", 500.0)
            partsAPI.add(part1)
            partsAPI.add(part2)
            assertEquals(part2, partsAPI.getPart(1))
        }

        @Test
        fun `test invalid part index in getPart`() {
            assertNull(partsAPI.getPart(0))
        }
    }

    @Nested
    inner class updatePart {
        @Test
        fun `test update part`() {
            val part = Parts("Engine", "123456", "Toyota", 1000.0)
            partsAPI.add(part)

            val updatedPart = Parts("Engine", "123456", "Toyota", 1500.0)
            assertTrue(partsAPI.updatePart(0, updatedPart))
            assertEquals(updatedPart, partsAPI.getPart(0))
        }

        @Test
        fun `test invalid part index in updatePart`() {
            val part = Parts("Engine", "123456", "Toyota", 1000.0)
            assertFalse(partsAPI.updatePart(0, part))
        }
    }

    @Test
    fun `test list all parts`() {
        val part1 = Parts("Engine", "123456", "Toyota", 1000.0)
        val part2 = Parts("Battery", "789012", "Honda", 500.0)
        partsAPI.add(part1)
        partsAPI.add(part2)
        assertEquals("0: $part1\n1: $part2", partsAPI.listAllParts())
    }

    @Nested
    inner class searchByNameOrNumber {
        @Test
        fun `test search by name`() {
            val part1 = Parts("Engine", "123456", "Toyota", 1000.0)
            val part2 = Parts("Battery", "789012", "Honda", 500.0)
            partsAPI.add(part1)
            partsAPI.add(part2)
            assertEquals(part1, partsAPI.searchByNameOrNumber("Engine"))
        }

        @Test
        fun `test search by number`() {
            val part1 = Parts("Engine", "123456", "Toyota", 1000.0)
            val part2 = Parts("Battery", "789012", "Honda", 500.0)
            partsAPI.add(part1)
            partsAPI.add(part2)
            assertEquals(part2, partsAPI.searchByNameOrNumber("789012"))
        }

        @Test
        fun `test invalid search by name or number`() {
            assertNull(partsAPI.searchByNameOrNumber("Invalid"))
        }
    }
}


