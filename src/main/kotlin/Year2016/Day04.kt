package Year2016

import Day

class Day04: Day(2016,4) {

    val roomList = input.asList.filterNotEmpty().map {
        val name = it.substringBeforeLast('-')
        val sectorAndChecksum = it.substringAfterLast('-')
        val sectorId = sectorAndChecksum.substringBefore('[').toInt()
        val checksum = sectorAndChecksum.substringAfter('[').dropLast(1)
        Room(name,sectorId,checksum)
    }

    override fun part1(): Any {
        return roomList.filter { it.isReal }.sumBy { it.sectorId }
    }

    override fun part2(): Any {
        return roomList.filter { it.isReal }.filter { it.encryptedName.decodeCypher(it.sectorId).contains("north") }.first().sectorId
    }

    data class Room(val encryptedName: String, val sectorId: Int, val checksum: String) {

        val isReal: Boolean get() = calculatedChecksum == checksum

        private val calculatedChecksum: String get() = encryptedName
            .filterNot { it == '-' } //Remove dashes
            .groupBy { it } //Group by char
            .map { it.key to it.value.size } //Count number of occurrences
            .sortedBy { it.first } //Sort alphabetically
            .sortedByDescending { it.second } //Sort by occurrences
            .take(5) //Take first 5
            .map { it.first } //Drop number of occurrences
            .joinToString("")
    }

    fun String.decodeCypher(shift: Int): String {
        val offset = shift % 26
        if (offset == 0) return this
        return map { c ->
            if (c in 'a'..'z') {
                var d = c + offset
                if (d > 'z') d -= 26
                d
            }
            else
                c
        }.joinToString("")
    }
}