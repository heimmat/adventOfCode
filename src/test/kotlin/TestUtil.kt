import util.convertFromHexToBinary
import kotlin.test.assertEquals
import kotlin.test.Test

class TestUtil {

    @Test
    fun testHexToBinary() {
        assertEquals("110100101111111000101000", "D2FE28".convertFromHexToBinary())
        assertEquals("00111000000000000110111101000101001010010001001000000000", "38006F45291200".convertFromHexToBinary())
    }
}