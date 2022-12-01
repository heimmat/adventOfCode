package training2022

import TestDay
import year2022.Day01

class TrainingDay01: TestDay(Day01(true)) {
    override val results: Pair<Any, Any> = 24000 to 45000
}