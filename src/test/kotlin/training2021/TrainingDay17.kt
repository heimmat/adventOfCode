package training2021

import TestDay
import util.x
import util.y
import year2021.Day17
import kotlin.test.Test
import kotlin.test.assertEquals


class TrainingDay17: TestDay(Day17(true)) {

    override val results: Pair<Any, Any>
        get() = 45 to Any()

    val day17 = day as Day17
    @Test
    fun testVelocities() {
        assertEquals(listOf(5 to 5, 4 to 4, 3 to 3, 2 to 2, 1 to 1, 0 to 0, 0 to -1), day17.velocitiesFromInitial(5 to 5).take(7).toList())
    }

    @Test
    fun testTrajectory() {
        assertEquals(
            listOf(0 to 0, 5 to 5, 9 to 9, 12 to 12, 14 to 14, 15 to 15, 15 to 15, 15 to 14, 15 to 12),
            day17.trajectoryFromInitialVelocity(5 to 5).take(9).toList()
        )
    }

    @Test
    fun testTakeWhileRangePossible() {
        val yRange = -10..-5
        val xRange = 20..30
        assertEquals(28 to -7, day17.trajectoryFromInitialVelocity(7 to 2).takeWhile {
            it.y >= yRange.first && it.x <= xRange.last
        }.last())
    }
}