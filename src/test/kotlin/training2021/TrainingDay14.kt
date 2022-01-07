package training2021

import TestDay
import year2021.Day14
import kotlin.test.Test
import kotlin.test.assertEquals

class TrainingDay14: TestDay(Day14(true)) {
    override val results: Pair<Any, Any> = 1588 to 2188189693529L

    private val day14: Day14 = day as Day14

//    private val rules = """
//       CH -> B
//       HH -> N
//       CB -> H
//       NH -> C
//       HB -> C
//       HC -> B
//       HN -> C
//       NN -> C
//       BH -> H
//       NC -> B
//       NB -> B
//       BN -> B
//       BB -> N
//       BC -> B
//       CC -> N
//       CN -> C
//    """.trimIndent().split("\n")
    private val rules = listOf(
        Day14.PolymerizationRule("CH", "B"),
        Day14.PolymerizationRule("HH", "N"),
        Day14.PolymerizationRule("CB", "H"),
        Day14.PolymerizationRule("NH", "C"),
        Day14.PolymerizationRule("HB", "C"),
        Day14.PolymerizationRule("HC", "B"),
        Day14.PolymerizationRule("HN", "C"),
        Day14.PolymerizationRule("NN", "C"),
        Day14.PolymerizationRule("BH", "H"),
        Day14.PolymerizationRule("NC", "B"),
        Day14.PolymerizationRule("NB", "B"),
        Day14.PolymerizationRule("BN", "B"),
        Day14.PolymerizationRule("BB", "N"),
        Day14.PolymerizationRule("BC", "B"),
        Day14.PolymerizationRule("CC", "N"),
        Day14.PolymerizationRule("CN", "C")
    )

    @Test
    fun testTemplate() {
        assertEquals("NNCB", day14.template)
    }

    @Test
    fun testRules() {
        assertEquals(rules, day14.rules)
    }
}