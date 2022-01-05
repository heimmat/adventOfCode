package year2021

import Day

class Day14(debug: Boolean = false): Day(2021,14, debug) {
   private val testInput = """
       NNCB

       CH -> B
       HH -> N
       CB -> H
       NH -> C
       HB -> C
       HC -> B
       HN -> C
       NN -> C
       BH -> H
       NC -> B
       NB -> B
       BN -> B
       BB -> N
       BC -> B
       CC -> N
       CN -> C
   """.trimIndent().split("\n")

   private val normalizedInput = (if (debug) testInput else input.asList)

    val template = normalizedInput.first()
    val rules = normalizedInput.drop(2).map {
        val split = it.split(" -> ")
        PolymerizationRule(split.first(), split.last())
    }

    class PolymerizationRule(val template: String, val toInsert: String) {
        val regex = Regex(template)
        fun applyOn(string: CharSequence): String {
            return regex.replace(string, "${template[0]}$toInsert${template[1]}")
        }

        override fun equals(other: Any?): Boolean {
            return if (other is PolymerizationRule) {
                this.template == other.template && this.toInsert == other.toInsert
            } else {
                super.equals(other)
            }
        }
    }

    override fun part1(): Any {
        var str = template
        repeat(10) {
            str = str.polymerize()
        }
        val grouped = str.groupBy {it}
        return grouped.maxOf { it.value.size } - grouped.minOf { it.value.size }
    }

    private fun String.polymerize(): String {
        return this.windowed(2).map { cand ->
            rules.map { it.applyOn(cand) }.first { it != cand }
        }.joinToString(separator = "", postfix = "${this.last()}") {
            it.dropLast(1)
        }
    }

    //Inspired by https://todd.ginsberg.com/post/advent-of-code/2021/day14/
    override fun part2(): Any {
        val template: Map<CharSequence, Long> = this.template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        val rules: Map<CharSequence, Char> = this.rules.associate { it.template to it.toInsert.first() }


        fun solve(iterations: Int): Long =
            (0 until iterations)
                .fold(template) { polymer, _ -> polymer.react(rules) }
                .byCharFrequency()
                .values
                .sorted()
                .let { it.last() - it.first() }


        return solve(40)
    }

    @OptIn(kotlin.ExperimentalStdlibApi::class)
    fun Map<CharSequence,Long>.react(rules: Map<CharSequence, Char>): Map<CharSequence,Long> = buildMap {
        this@react.forEach { pair, count ->
            val inserted = rules.getValue(pair)
            plus("${pair.first()}$inserted", count)
            plus("$inserted${pair.last()}", count)
        }
    }

    private fun <T> MutableMap<T, Long>.plus(key: T, amount: Long) {
        this[key] = this.getOrDefault(key, 0L) + amount
    }

    private fun Map<CharSequence, Long>.byCharFrequency(): Map<Char, Long> =
    this
        .map { it.key.first() to it.value }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() + if (it.key == template.last()) 1 else 0 }


}
