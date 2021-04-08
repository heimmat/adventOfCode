package year2017

import Day
import util.plus
import util.times
import kotlin.math.abs

class Day20 : Day(2017,20) {
    private val testParticles = listOf(
        "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>",
        "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"
    ).map { parseParticle(it) }

    val particles = input.asList.filterNotEmpty().map { parseParticle(it) }

    override fun part1(): Any {
        val setOfOrders = mutableListOf<List<Particle>>()
        var tick = 0
        var particlesByDistance: List<Particle> =particles.orderByDistanceAtTick(tick)
        while (setOfOrders.isEmpty() || particlesByDistance != setOfOrders.last() || tick < 10) {
            setOfOrders.add(particlesByDistance)
            tick++
            particlesByDistance = particles.orderByDistanceAtTick(tick)
        }
        return particles.indexOf(setOfOrders.last().first())

    }

    override fun part2(): Any {
        val testParticles = listOf(
            "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
            "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
            "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
            "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"
        ).map { parseParticle(it) }
        var particles = particles
        var tick = 0
        var collisionsRemoved = particles.removeCollision(tick)
        while (particles != collisionsRemoved || tick < 100) {
            particles = collisionsRemoved
            tick++
            collisionsRemoved = particles.removeCollision(tick)
        }
        return particles.size
    }

    fun List<Particle>.orderByDistanceAtTick(tick: Int) = this.sortedBy {
        val position = it.positionAtTick(tick)
        abs(position.first) + abs(position.second) + abs(position.third)
    }

    fun List<Particle>.removeCollision(tick: Int): List<Particle> {
        val grouped = this.groupBy {
            it.positionAtTick(tick)
        }
        val collisions = grouped.filter { it.value.size > 1 }.flatMap { it.value }
        return this - collisions
    }

    private fun parseParticle(input: String): Particle {

        fun String.parseTriple(): Triple<Int,Int,Int> {
            val split = this.split(",")
            if (split.size == 3) {
                val ints = split.map { it.trim().toInt() }
                return Triple(ints[0], ints[1], ints[2])
            } else {
                throw IllegalArgumentException(split.toString())
            }
        }
        val posRegex = Regex("""(?<=p=\<)[\s\d\,-]+(?=\>)""")
        val velRegex = Regex("""(?<=v=\<)[\s\d\,-]+(?=\>)""")
        val accRegex = Regex("""(?<=a=\<)[\s\d\,-]+(?=\>)""")

        return Particle(
            posRegex.find(input)!!.value.parseTriple(),
            velRegex.find(input)!!.value.parseTriple(),
            accRegex.find(input)!!.value.parseTriple(),
        )

    }

    data class Particle(val position: Triple<Int,Int,Int>,
                        val velocity: Triple<Int,Int,Int>,
                        val acceleration: Triple<Int,Int,Int>) {
        private val velocities = generateSequence(velocity) {
            it + acceleration
        }

        fun velocityAtTick(tick: Int): Triple<Int,Int,Int> {
            return velocity + (acceleration * tick)
        }

        private val positions = generateSequence(0 to position) {
            it.first + 1 to it.second + velocityAtTick(it.first + 1)
        }.map { it.second }

        fun positionAtTick(tick: Int): Triple<Int,Int,Int> {
            return positions.elementAt(tick)
        }



    }


}
