package be.koder.maxi.vocabulary.search

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CategoryTest {

    @Test
    fun `should create category with valid string`() {
        val category = Category.create("Technology")
        assertEquals("Technology", category.toString())
    }

    @Test
    fun `should throw exception when creating category with empty string`() {
        assertThrows<IllegalArgumentException> {
            Category.create("")
        }
    }

    @Test
    fun `should sanitize input string`() {
        val category = Category.create("  Technology  ")
        assertEquals("Technology", category.toString())
    }

    @Test
    fun `should be equal when values are the same`() {
        val category1 = Category.create("Science")
        val category2 = Category.create("Science")
        assertEquals(category1, category2)
        assertEquals(category1.hashCode(), category2.hashCode())
    }

    @Test
    fun `should not be equal when values are different`() {
        val category1 = Category.create("Science")
        val category2 = Category.create("Technology")
        assertNotEquals(category1, category2)
    }
}