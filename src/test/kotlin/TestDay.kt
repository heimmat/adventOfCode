import kotlin.test.Test
import kotlin.test.assertEquals

abstract class TestDay(val day: Day) {
    abstract val results: Pair<Any,Any>
    protected open val input = day.input

    @Test
    fun testPart1() {
        assertEquals(results.first, day.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(results.second, day.part2())
    }

}