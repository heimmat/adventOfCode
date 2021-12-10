package training2018

import TestDay
import year2018.Day07

class TrainingDay07: TestDay(Day07(true)) {
    override val results: Pair<Any, Any> = "CABDFE" to 15
}