package year2021

import Day
import util.convertFromHexToBinary
import util.next as utilNext

class Day16(debug: Boolean = false): Day(2021,16, debug) {
    override fun part1(): Any {
        val binaryRepresentation = (if (debug) "A0016C880162017C3686B18A3D4780" else input.asString.trim()).convertFromHexToBinary()
        return BITS.of(binaryRepresentation.iterator()).sumOfVersions
    }

    override fun part2(): Any {
        val binaryRepresentation = (if (debug) "9C0141080250320F1802104A08" else input.asString.trim()).convertFromHexToBinary()
        return BITS.of(binaryRepresentation.iterator()).value
    }

    class BITSPacket(binaryRepresentation: String) {
        val version = binaryRepresentation.take(3).toInt(2)
        val type = binaryRepresentation.drop(3).take(3).toInt(2)
        val lengthId: Char? = if (type != 4) {
            binaryRepresentation.drop(6).first()
        } else null

        val lengthBits = when (lengthId) {
            '0' -> 15
            '1' -> 11
            else -> null
        }

        val numSubpackets = if (lengthBits == 11) {
            binaryRepresentation.drop(7).take(lengthBits).toInt(2)
        } else null

        val subPacketBitCount = if (lengthBits == 15) {
            binaryRepresentation.drop(7).take(lengthBits).toInt(2)
        } else null

        val literalValue: Int? = if (type == 4) {
            var continueTaking = true
            binaryRepresentation
                .drop(6) //Drop version and type
                .windowed(5,5) //Look at 5 bits each
                .takeWhile { s ->
                    continueTaking.also { continueTaking = s.first() == '1' }
                }
                .map { it.drop(1) }
                .joinToString("")
                .toInt(2)
        } else null


    }


    //https://todd.ginsberg.com/post/advent-of-code/2021/day16/

    sealed class BITS(val version: Int, val type: Int) {

        abstract val sumOfVersions: Int
        abstract val value: Long


        companion object {
            fun Iterator<Char>.next(n: Int): String = this.utilNext(n).joinToString("")
            fun Iterator<Char>.nextInt(n: Int): Int = this.next(n).toInt(2)

            fun Iterator<Char>.nextUntilFirst(size: Int, stopCondition: (String) -> Boolean): List<String> {
                val output = mutableListOf<String>()
                do {
                    val readValue = next(size)
                    output.add(readValue)
                } while (!stopCondition(readValue))
                return output
            }

            fun <T> Iterator<Char>.executeUntilEmpty(function: (Iterator<Char>) -> T): List<T> {
                val output = mutableListOf<T>()
                while (this.hasNext()) {
                    output.add(function(this))
                }
                return output
            }

            fun of(input: Iterator<Char>): BITS {
                val version = input.nextInt(3)
                val type = input.nextInt(3)
                return when (type) {
                    4 -> BITSLiteral.of(version, input)
                    else -> BITSOperator.of(version, type, input)
                }
            }
        }

        class BITSLiteral(version: Int, val literal: Long): BITS(version, 4) {
            override val sumOfVersions: Int get() = version
            override val value: Long
                get() =  literal
            companion object {
                fun of(version: Int, input: Iterator<Char>): BITSLiteral {
                    return BITSLiteral(version, parseLiteral(input))
                }

                private fun parseLiteral(input: Iterator<Char>): Long = input
                    .nextUntilFirst(5) {
                        it.first() == '0'
                    }
                    .map { it.drop(1) }
                    .joinToString("")
                    .toLong(2)
            }
        }

        class BITSOperator(version: Int, type: Int, val subPackets: List<BITS>): BITS(version, type) {
            override val value: Long get() {
                return when (type) {
                    0 -> subPackets.sumOf { it.value }
                    1 -> subPackets.fold(1L) { acc, bits ->
                        acc * bits.value
                    }
                    2 -> subPackets.minOf {
                        it.value
                    }
                    3 -> subPackets.maxOf {
                        it.value
                    }
                    5 -> if (subPackets.first().value > subPackets.last().value) 1 else 0
                    6 -> if (subPackets.first().value < subPackets.last().value) 1 else 0
                    7 -> if (subPackets.first().value == subPackets.last().value) 1 else 0
                    else -> error("Invalid Operator type")
                }
            }
            override val sumOfVersions: Int
                get() = version + subPackets.sumOf { it.sumOfVersions }
            companion object {
                fun of(version: Int, type: Int, input: Iterator<Char>): BITSOperator {
                    val length = input.nextInt(1)
                    return when (length) {
                        0 -> {
                            val subPacketLength = input.nextInt(15)
                            val subPacketIterator = input.next(subPacketLength).iterator()
                            val subPackets = subPacketIterator.executeUntilEmpty { of(it) }
                            BITSOperator(version, type, subPackets)
                        }
                        1 -> {
                            val numberOfPackets = input.nextInt(11)
                            val subPackets = (1..numberOfPackets).map { of(input) }
                            BITSOperator(version, type, subPackets)
                        }
                        else -> error("Invalid length operator")
                    }
                }
            }
        }


    }


}