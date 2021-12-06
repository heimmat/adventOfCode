package training2021

import TestDay
import year2021.Day04
import kotlin.test.Test
import kotlin.test.assertEquals

class TrainingDay04: TestDay(Day04(true)) {
    val castedDay = day as Day04

    override val results: Pair<Any, Any> = 4512 to 1924

    @Test
    fun testParsing() {
        assertEquals(listOf(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1), castedDay.drawnNumbers)
        assertEquals(22, castedDay.bingoTables.first().table.get(0 to 0))
        assertEquals(7, castedDay.bingoTables.last()[4 to 4])
    }
}