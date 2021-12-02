package year2021

import Day
import Year

class Year2021: Year(2021) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01()
    )

}