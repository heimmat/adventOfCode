package Year2016

import Day

class Day09 : Day(2016,9) {
    val regexForMarker = Regex("""\((\d+)x(\d+)\)""")

    private fun String.nextMarker(startIndex: Int = 0): Marker? {
        val match = regexForMarker.find(this, startIndex)
        return match?.run {
            val contentLength = this.groups[1]!!.value.toInt()
            val repetitions = this.groups[2]!!.value.toInt()
            val marker = Marker(range, range.last + 1..range.last + contentLength, repetitions)
            return marker
        }
    }

    private fun String.decode(): String {
        var nextMarker: Marker? = null
        var startIndex = 0
        val builder = StringBuilder()
        do {
            nextMarker = nextMarker(startIndex)
            nextMarker?.let {
                builder.append(this.substring(startIndex until nextMarker.markerRange.first))
                startIndex = nextMarker.contentRange.last + 1
                repeat(nextMarker.repetitions) {
                    builder.append(this.substring(nextMarker.contentRange))
                }
            }

        } while (nextMarker != null)
        return builder.toString()

    }

    private fun String.decodedLength(): Long {
        var nextMarker: Marker?
        var startIndex = 0
        var decodedLength = 0L
        do {
            nextMarker = nextMarker(startIndex)
            nextMarker?.let {
                decodedLength += nextMarker.markerRange.first - startIndex
                startIndex = nextMarker.contentRange.last + 1
                //length += nextMarker.repetitions * (nextMarker.contentRange.last + 1 - nextMarker.contentRange.first)
                decodedLength += nextMarker.repetitions * substring(nextMarker.contentRange).decodedLength()

            }
        } while (nextMarker != null)
        if (decodedLength == 0L) decodedLength = length.toLong()
        return decodedLength
    }

    private data class Marker(val markerRange: IntRange, val contentRange: IntRange, val repetitions: Int)

    override fun part1(): Any {
        return input.asString.decode().length
    }

    override fun part2(): Any {
        return input.asString.decodedLength()
    }

}

