import util.convertFromHexToBinary
import util.isTouching
import util.moveTowards
import kotlin.test.assertEquals
import kotlin.test.Test

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
}