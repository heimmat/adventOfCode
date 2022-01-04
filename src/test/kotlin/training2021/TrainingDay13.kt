package training2021

import TestDay
import year2021.Day13
import kotlin.test.Test
import kotlin.test.assertEquals

class TrainingDay13: TestDay(Day13(true)) {
    override val results: Pair<Any, Any> = 17 to """
        #####
        #...#
        #...#
        #...#
        #####
    """.trimIndent()
    private val day13: Day13 = this.day as Day13

    private val testPoints = """  
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0
    """.trimIndent().split("\n").map {
        val split = it.split(",").map { it.toInt() }
        split.first() to split.last()
    }

    private val testInstructions = listOf(
        "fold along y=7",
        "fold along x=5"
    )

    @Test
    fun testPoints() {
        assertEquals(testPoints, day13.points)
    }

    @Test
    fun testInstructions() {
        assertEquals(testInstructions, day13.instructions)
    }
}