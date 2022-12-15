import util.*
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertFails

class TestUtil {

    @Test
    fun testHexToBinary() {
        assertEquals("110100101111111000101000", "D2FE28".convertFromHexToBinary())
        assertEquals("00111000000000000110111101000101001010010001001000000000", "38006F45291200".convertFromHexToBinary())
    }

    @Test
    fun testTouching() {
        assert((0 to 0).isTouching(0 to 0))
        assert((0 to 0).isTouching(1 to 0))
        assert((0 to 0).isTouching(0 to 1))
        assert((0 to 0).isTouching(-1 to 0))
        assert((0 to 0).isTouching(0 to -1))
        assert((0 to 0).isTouching(1 to 1))
        assert((0 to 0).isTouching(1 to -1))
        assert((0 to 0).isTouching(-1 to 1))
        assert((0 to 0).isTouching(-1 to -1))
        assert(!(0 to 0).isTouching(2 to 0))
    }

    @Test
    fun testMoveTowards() {
        assertEquals((1 to 0),(0 to 0).moveTowards(2 to 0))
        assertEquals(1 to 1, (0 to 0).moveTowards(2 to 2))
    }

    @Test
    fun testRangeTo() {
        assertEquals(listOf(-1 to 0, 0 to 0, 1 to 0, 2 to 0), (-1 to 0)..(2 to 0))
        assertFails { (-1 to -1)..(1 to 0) }
    }

    @Test
    fun testCoordinatesInManhattanDistance() {
        assertEquals(listOf(0 to 0, 0 to 1, 1 to 0, -1 to 0, 0 to -1).size, (0 to 0).coordinatesInManhattanDistance(1).size)
    }
}