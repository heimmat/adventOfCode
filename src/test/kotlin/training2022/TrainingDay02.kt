package training2022

import TestDay
import year2022.Day02

class TrainingDay02: TestDay(Day02(true)) {
    override val results: Pair<Any, Any> = 15 to 12
}