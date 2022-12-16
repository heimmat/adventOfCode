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
            .also { if (debug) println(it) }
            .sumOf { it.first }
    }

    override fun part2(): Any {
        val list2 = Day13List(listOf(Day13List(listOf(Day13Int(2)))))
        val list6 = Day13List(listOf(Day13List(listOf(Day13Int(6)))))
        val signals = (if (debug) testInput else input.asString).split("\n")
            .filterNotEmpty()
            .map { IntOrList.parse(it) }
        val sortedSignals = (signals + listOf(
            list2,
            list6
        )).sorted()
        return (sortedSignals.indexOf(list2) + 1) * (sortedSignals.indexOf(list6) + 1)
    }



    @Serializable
    abstract class IntOrList: Comparable<IntOrList> {
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

        override operator fun compareTo(other: IntOrList): Int {
            return if (this is Day13Int && other is Day13Int) {
                this.compareTo(other)
            } else if (this is Day13List && other is Day13List) {
                this.compareTo(other)
            } else if (this is Day13List && other is Day13Int) {
                this.compareTo(Day13List(listOf(other)))
            } else if (this is Day13Int && other is Day13List) {
                Day13List(listOf(this)).compareTo(other)
            }else {
                throw IllegalArgumentException()
            }
        }


    }
    @Serializable
    class Day13List(private val hiddenList: List<IntOrList>): IntOrList() {
        operator fun compareTo(that: Day13List): Int {
            //println("Compare $this vs $that")
            var index = 0
            var comparison = 0
            while (comparison == 0) {
                //println("Index is $index")
                //println("Indices of this: ${this.hiddenList.indices}")
                //println("Indices of that: ${that.hiddenList.indices}")
                if (index in this.hiddenList.indices && index in that.hiddenList.indices) {
                    comparison = this.hiddenList[index].compareTo(that.hiddenList[index])
                } else if (this.hiddenList.size == that.hiddenList.size) {
                    break
                } else if (index >= this.hiddenList.size) {
                    comparison = -1
                } else if (index >= that.hiddenList.size) {
                    comparison = 1
                } else {
                    //println("Breaking loop")
                    break
                }
                //println("comparison is $comparison at end of loop round")
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
            //println("Compare $this vs $that")
            return this.hiddenInt.compareTo(that.hiddenInt)
        }

        override fun toString(): String {
            return hiddenInt.toString()
        }
    }
}