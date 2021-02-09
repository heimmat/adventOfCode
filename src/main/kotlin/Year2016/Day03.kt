package Year2016

import Day

class Day03 : Day(2016,3) {

    override fun part1(): Any {
        return input.asList.filterNotEmpty().map {
            val (a,b,c) = it.trim().split(Regex("\\D+")).map { it.toInt() }
            Triple(a,b,c)
        }.filter { it.isTriangle() }.count()
    }

    override fun part2(): Any {
        val potentialTriangles = mutableListOf<Triple<Int,Int,Int>>()
        val newTriangles = input.asList.filterNotEmpty().map {
            it.trim().split(Regex("\\D+")).map { it.toInt() }
        }
        for (column in 0 until 3) {
            for (row in newTriangles.indices step 3) {
                potentialTriangles.add(Triple(newTriangles[row][column], newTriangles[row+1][column], newTriangles[row+2][column]))
            }
        }
        return potentialTriangles.count { it.isTriangle() }

    }

    fun isTriangle(a: Int, b: Int, c: Int) = Triple(a,b,c).isTriangle()

    private fun Triple<Int,Int,Int>.isTriangle(): Boolean = first + second > third && first + third > second && second + third > first
}
