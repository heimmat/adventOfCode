package year2018

import Day
import Year

class Year2018 : Year(2018) {
    override val days: Map<Int, Day> = mutableMapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03(),
        4 to Day04(),
        5 to Day05(),
        6 to Day06(),
        7 to Day07()
    )

}
