package year2022
import Day

class Day02(debug: Boolean = false): Day(2022, 2, debug) {
    private val testInput = """A Y
B X
C Z""".trimIndent()

    private val strategyGuide = (if (debug) testInput.split("\n") else input.asList)
        .map { it.split(" ") }
        .map { it.first() to it.last() }

    override fun part1(): Any {
        return strategyGuide.map {
            it.toScore() + it.second.toScore()
        }.sum()
    }

    fun String.toScore(): Int = when (this) {
        "X" -> 1
        "Y" -> 2
        "Z" -> 3
        else -> 0
    }

    fun Pair<String,String>.toScore(): Int = when (this) {
        "A" to "X" -> 3
        "B" to "Y" -> 3
        "C" to "Z" -> 3
        //Wins
        "A" to "Y" -> 6
        "B" to "Z" -> 6
        "C" to "X" -> 6
        //Losses
        "B" to "X" -> 0
        "C" to "Y" -> 0
        "A" to "Z" -> 0
        else -> throw IllegalArgumentException()
    }

    fun Pair<String,String>.drawAccordingToStrategy(): String {
        return when (this.second) {
            //Loss
            "X" -> {
                when (this.first) {
                    "A" -> "Z"
                    "B" -> "X"
                    "C" -> "Y"
                    else -> throw IllegalArgumentException()
                }
            }
            //Draw
            "Y" -> {
                when (this.first) {
                    "A" -> "X"
                    "B" -> "Y"
                    "C" -> "Z"
                    else -> throw IllegalArgumentException()
                }
            }
            //Win
            "Z" -> {
                when (this.first) {
                    "A" -> "Y"
                    "B" -> "Z"
                    "C" -> "X"
                    else -> throw IllegalArgumentException()
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    fun String.scoreAsResult(): Int = when (this) {
        "X" -> 0
        "Y" -> 3
        "Z" -> 6
        else -> throw IllegalArgumentException()
    }

    override fun part2(): Any {
        return strategyGuide.map {
            it.drawAccordingToStrategy().toScore() + it.second.scoreAsResult()
        }.sum()
    }
}