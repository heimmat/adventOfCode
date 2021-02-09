package Year2015

import Day

class Day03 : Day(2015,3) {
    override fun part1(): String {
        val houses = mutableMapOf<Pair<Int,Int>,Int>()
        var north = 0
        var east = 0
        for (direction in input.asString) {
            houses.compute(Pair(east, north)) { key: Pair<Int,Int>, value: Int? ->
                if (value == null) 1 else value + 1
            }
            when (direction) {
                '<' -> east--
                '>' -> east++
                '^' -> north++
                'v' -> north--
            }
        }
        return houses.size.toString()
    }

    override fun part2(): String {
        val houses = mutableMapOf<Pair<Int,Int>,Int>()
        val positions = mutableListOf(Pair(0,0), Pair(0,0))
        positions.forEach {
            houses.compute(it) { key, value ->
                if (value == null) 1 else value + 1

            }
        }
        input.asString.forEachIndexed { index, char ->
            positions[index%2] = when(char) {
                '<' -> Pair(positions[index%2].first-1, positions[index%2].second)
                '>' -> Pair(positions[index%2].first+1, positions[index%2].second)
                '^' -> Pair(positions[index%2].first, positions[index%2].second+1)
                'v' -> Pair(positions[index%2].first, positions[index%2].second-1)
                else -> throw NotImplementedError()
            }
            houses.compute(positions[index%2]) { key, value ->
                if (value == null) 1 else value + 1
            }
        }
        return houses.size.toString()
    }


}
