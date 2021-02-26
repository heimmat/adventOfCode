package Year2016

import Day
import util.md5Hex

class Day17 : Day(2016,17) {

    val passcode = input.asString.trim()


    override fun part1(): Any {
        return 0 shortestPathTo 15 ?: "No path found"
    }

    override fun part2(): Any {
        return (0 longestPathTo 15)?.length ?: "No path found"
    }

    fun List<Room>.allRooms(except: Int): List<Room> {
        return flatMap {
            if (it.id == except) listOf(it) else it.nextRooms().allRooms(except)
        }
    }

    inner class Room(val id: Int, val pathTaken: String) {
        val doors: Map<Direction, Int> = Direction.values().mapNotNull {
            val nextRoom = it.moveFrom(id)
            if (nextRoom != null) it to nextRoom else null
        }.toMap()

        private val hash = "$passcode$pathTaken".md5Hex()

        private val openDoors = doors.filter { hash[it.key.ordinal] in "bcdef" }

        val canLeave = openDoors.isNotEmpty()

        fun nextRooms(): List<Room> {
            return openDoors.map { Room(it.value, "$pathTaken${it.key}") }
        }

        override fun equals(other: Any?): Boolean {
            return if (other is Room) {
                this.id == other.id
            } else {
                false
            }
        }

        override fun hashCode(): Int {
            return id.hashCode()
        }
    }

    enum class Direction(private val offset: Int, private val check: (Int) -> Boolean) {
        U(-4, { it > 3 }),
        D(4, { it < 12 }),
        L(-1, { (it % 4) > 0 }),
        R(1, { (it % 4) < 3 });

        fun isPossibleAt(id: Int): Boolean = check(id)
        fun moveFrom(id: Int): Int? = if (isPossibleAt(id)) id + offset else null
    }

    infix fun Int.longestPathTo(other: Int): String? {
        val validRange = 0..15
        if (this in validRange && other in validRange) {
            val allRooms = listOf(Room(0,"")).allRooms(other)
            return allRooms.maxByOrNull { it.pathTaken.length }?.pathTaken
        } else {
            throw IllegalArgumentException()
        }
    }

    infix fun Int.shortestPathTo(other: Int): String? {
        val validRange = 0..15
        if (this in validRange && other in validRange) {
            val openList: MutableList<Room> = mutableListOf(Room(0,""))
            val closedList: MutableList<Room> = mutableListOf()

            var found  = false

            while (!found) {
                val shortestYet = openList.minByOrNull { it.pathTaken.length }
                if (shortestYet != null) {
                    println("Best guess is ${shortestYet.id}/${shortestYet.pathTaken}")
                    openList.remove(shortestYet)
                    if (shortestYet.id == other) {
                        found = true
                        return shortestYet.pathTaken
                    }
                    val nextSteps = shortestYet.nextRooms().filter { it.canLeave || it.id == other }
                    openList.addAll(nextSteps)
                    closedList.add(shortestYet)
                } else {
                    break
                }
            }
            return null




        } else {
            throw IllegalArgumentException()
        }
    }
}
