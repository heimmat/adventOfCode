package training2021

import TestDay
import util.convertFromHexToBinary
import year2021.Day16
import kotlin.test.Test
import kotlin.test.assertEquals

class TrainingDay16: TestDay(Day16(true)) {
    override val results: Pair<Any, Any> = 31 to 1L

    @Test
    fun testLiteralPacket() {
        assertEquals(2021, Day16.BITSPacket("110100101111111000101000").literalValue)
        assertEquals(10, Day16.BITSPacket("11010001010").literalValue)
        assertEquals(20, Day16.BITSPacket("0101001000100100").literalValue)

        assertEquals(2021, (Day16.BITS.of("110100101111111000101000".iterator()) as Day16.BITS.BITSLiteral).literal)
        assertEquals(10,(Day16.BITS.of("11010001010".iterator()) as Day16.BITS.BITSLiteral).literal)
        assertEquals(20, (Day16.BITS.of("0101001000100100".iterator()) as Day16.BITS.BITSLiteral).literal)
    }


}