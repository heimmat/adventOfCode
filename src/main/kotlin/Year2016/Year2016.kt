package Year2016

import Day
import Year

class Year2016 : Year(2016) {
    override val days: Map<Int, Day> = mapOf(
        1 to Day01()
    )
}
