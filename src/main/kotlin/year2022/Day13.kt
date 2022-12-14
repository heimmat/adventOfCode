package year2022

import Day
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

class Day13(debug: Boolean = false): Day(2022,13, debug) {
    private val testInput = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    private val distressSignal = (if (debug) testInput else input.asString).split("\n\n")
        .map {
            IntOrList.parse(it.substringBefore("\n")) to IntOrList.parse(it.substringAfter("\n"))
        }

    override fun part1(): Any {
        return distressSignal
            .mapIndexed { index, pair -> index + 1 to (pair.first < pair.second) }
            .filter { it.second }
            .also { println(it) }
            .sumOf { it.first }
    }



    @Serializable
    abstract class IntOrList {
        companion object {
            fun parse(str: String): IntOrList {
                //val json = Json.decodeFromString<IntOrList>(str)
                val json = Json.parseToJsonElement(str)
                assert(json is JsonArray)
                return json.toIntOrList()
            }

            fun JsonElement.toIntOrList(): IntOrList {
                return if (this is JsonArray) {
                    Day13List(this.map { it.toIntOrList() })
                } else if (this is JsonPrimitive) {
                    Day13Int(this.content.toInt())
                } else {
                    throw IllegalArgumentException("$this is neither List nor Int")
                }
            }
        }

        operator fun compareTo(that: IntOrList): Int {
            return if (this is Day13Int && that is Day13Int) {
                this.compareTo(that)
            } else if (this is Day13List && that is Day13List) {
                this.compareTo(that)
            } else if (this is Day13List && that is Day13Int) {
                this.compareTo(Day13List(listOf(that)))
            } else if (this is Day13Int && that is Day13List) {
                Day13List(listOf(this)).compareTo(that)
            }else {
                throw IllegalArgumentException()
            }
        }


    }
    @Serializable
    class Day13List(private val hiddenList: List<IntOrList>): IntOrList() {
        operator fun compareTo(that: Day13List): Int {
            println("Compare $this vs $that")
            var index = 0
            var comparison = 0
            while (comparison == 0) {
                if (index in this.hiddenList.indices && index in that.hiddenList.indices) {
                    comparison = this.hiddenList[index].compareTo(that.hiddenList[index])
                } else if (index >= this.hiddenList.size) {
                    comparison = -1
                } else if (index >= that.hiddenList.size) {
                    comparison = 1
                } else {
                    break
                }
                index++
            }
            return comparison
        }

        override fun toString(): String {
            return hiddenList.toString()
        }
    }
    @Serializable
    class Day13Int(private val hiddenInt: Int): IntOrList() {
        operator fun compareTo(that: Day13Int): Int {
            println("Compare $this vs $that")
            return this.hiddenInt.compareTo(that.hiddenInt)
        }

        override fun toString(): String {
            return hiddenInt.toString()
        }
    }
}