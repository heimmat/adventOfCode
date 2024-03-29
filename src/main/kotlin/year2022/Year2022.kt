package year2022

import Day
import Year

class Year2022: Year(2022) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03(),
        4 to Day04(),
        5 to Day05(),
        6 to Day06(),
        7 to Day07(),
        8 to Day08(),
        9 to Day09(),

        11 to Day11(),

        13 to Day13(),
        14 to Day14(),
        15 to Day15(),
        16 to Day16(),
    )
}