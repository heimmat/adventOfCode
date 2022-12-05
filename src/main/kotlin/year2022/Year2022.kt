package year2022

import Day
import Year

class Year2022: Year(2022) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        3 to Day03(),
        4 to Day04(),
        5 to Day05()
    )
}