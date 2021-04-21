package year2018

import Day
import Year

class Year2018 : Year(2018) {
    override val days: Map<Int, Day> = mutableMapOf(
        1 to Day01(),
        2 to Day02()
    )

}
