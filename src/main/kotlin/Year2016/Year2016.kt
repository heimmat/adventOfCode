package Year2016

import Day
import Year

class Year2016 : Year(2016) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03(),
        4 to Day04()
    )
}
