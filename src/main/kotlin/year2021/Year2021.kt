package year2021

import Day
import Year

class Year2021: Year(2021) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03(),
        4 to Day04()
    )

}