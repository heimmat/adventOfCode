package Year2015

import Day

class Day09 : Day(2015,9) {
    val test = listOf("London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141")

    private val distances = input.asList.filterNotEmpty().map { Distance.fromString(it) }
    private val endpoints: Set<String> = distances.fold(mutableSetOf()) { set, distance ->
        set.also { it.addAll(distance.endpoints) }
    }

    private fun getTotalDistance(endpoints: List<String>): Int {
        var distance = 0
        for (i in 1..endpoints.lastIndex) {
            distance += distances.first { it.endpoints.containsAll(endpoints.subList(i-1, i+1)) }.distance
        }
        return distance
    }

    //https://stackoverflow.com/a/63532094
    private fun <T> allPermutations(set: Set<T>): Set<List<T>> {
        if (set.isEmpty()) return emptySet()

        fun <T> _allPermutations(list: List<T>): Set<List<T>> {
            if (list.isEmpty()) return setOf(emptyList())

            val result: MutableSet<List<T>> = mutableSetOf()
            for (i in list.indices) {
                _allPermutations(list - list[i]).forEach{
                    item -> result.add(item + list[i])
                }
            }
            return result
        }

        return _allPermutations(set.toList())
    }

    override fun part1(): String {
        val shortestTrip = allPermutations(endpoints).map { it to getTotalDistance(it) }.minByOrNull { it.second }
        return shortestTrip?.second.toString()
    }

    override fun part2(): String {
        val longestTrip = allPermutations(endpoints).map { it to getTotalDistance(it) }.maxByOrNull { it.second }
        return longestTrip?.second.toString()
    }

    private data class Distance(val endpoints: List<String>, val distance: Int) {
        companion object {
            fun fromString(string: String): Distance {
                val (lh,rh) = string.split(" = ")
                val distance = rh.toInt()
                val endpoints = lh.split(" to ")
                return Distance(endpoints, distance)
            }
        }
    }
}
