package year2017

import Day
import Year

class Year2017: Year(2017) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03()
    )

}