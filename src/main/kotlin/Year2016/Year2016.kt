package Year2016

import Day
import Year

class Year2016 : Year(2016) {
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
        10 to Day10(),
        11 to Day11(),
        12 to Day12(),
        13 to Day13(),
        14 to Day14(),
        15 to Day15(),
        16 to Day16(),
        17 to Day17(),
        18 to Day18()
    )
}
