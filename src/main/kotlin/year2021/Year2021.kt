package year2021

import Day
import Year

class Year2021: Year(2021) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03(),
        4 to Day04(),
        5 to Day05(),
        6 to Day06(),
        7 to Day07(),
        8 to Day08(),
        9 to Day09()
    )

}