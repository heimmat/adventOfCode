package Year2015

import Day

class Day13 : Day(2015,13) {
    private val testInput = """
        Alice would gain 54 happiness units by sitting next to Bob.
        Alice would lose 79 happiness units by sitting next to Carol.
        Alice would lose 2 happiness units by sitting next to David.
        Bob would gain 83 happiness units by sitting next to Alice.
        Bob would lose 7 happiness units by sitting next to Carol.
        Bob would lose 63 happiness units by sitting next to David.
        Carol would lose 62 happiness units by sitting next to Alice.
        Carol would gain 60 happiness units by sitting next to Bob.
        Carol would gain 55 happiness units by sitting next to David.
        David would gain 46 happiness units by sitting next to Alice.
        David would lose 7 happiness units by sitting next to Bob.
        David would gain 41 happiness units by sitting next to Carol.
    """.trimIndent()
    private val happinessVectors = input.asList.filterNotEmpty().map { HappinessVector.fromString(it) }.toHappinessMap()
    private val attendees = happinessVectors.map { it.key.first }.toSet()
    private val attendeePermutations = attendees.allPermutations()

    override fun part1(): String {
        return attendeePermutations.map {
            totalHappiness(it.toSet())
        }.maxOf { it }.toString()
    }

    override fun part2(): String {
        return attendees.toMutableSet().also { it.add("me") }.allPermutations().map {
            totalHappiness(it.toSet())
        }.maxOf { it }.toString()
    }

    private fun totalHappiness(attendees: Set<String>): Int {
        var prevAttendee = attendees.first()
        var sum = 0
        attendees.drop(1).forEach {
            sum += happinessVectors[Pair(prevAttendee,it)] ?: 0
            sum += happinessVectors[Pair(it,prevAttendee)] ?: 0
            prevAttendee = it
        }
        sum += happinessVectors[Pair(prevAttendee, attendees.first())] ?: 0
        sum += happinessVectors[Pair(attendees.first(), prevAttendee)] ?: 0
        return sum

    }

    private data class HappinessVector(val from: String, val to: String,  val happinessDifference: Int) {
        companion object {
            fun fromString(string: String): HappinessVector {
                val split = string.split(" ")
                val from = split[0]
                val to = split.last().dropLast(1)
                val sign = if (split[2] == "gain") '+' else '-'
                val difference = "$sign${split[3]}".toInt()
                return HappinessVector(from,to,difference)
            }
        }
    }

    private fun List<HappinessVector>.toHappinessMap(): Map<Pair<String,String>,Int> {
        val map = mutableMapOf<Pair<String,String>,Int>()
        forEach {
            map.put(Pair(it.from,it.to),it.happinessDifference)
        }
        return map
    }
}

//https://stackoverflow.com/a/63532094
private fun <T> Set<T>.allPermutations(): Set<List<T>> {
    if (isEmpty()) return emptySet()

    fun <T> _allPermutations(list: List<T>): Set<List<T>> {
        if (list.isEmpty()) return setOf(emptyList())

        val result: MutableSet<List<T>> = mutableSetOf()
        for (i in list.indices) {
            _allPermutations(list - list[i]).forEach { item ->
                result.add(item + list[i])
            }
        }
        return result
    }

    return _allPermutations(toList())
}