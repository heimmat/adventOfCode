package Year2016

import Day
import Year2015.CombinationGenerator
import kotlin.math.abs

class Day11 : Day(2016,11) {

    /*val initialFloors = listOf(
        setOf(Device.Chip(Element.HYDROGEN), Device.Chip(Element.LITHIUM)),
        setOf(Device.Generator(Element.HYDROGEN)),
        setOf(Device.Generator(Element.LITHIUM)),
        emptySet()
    )*/

    private val initialFloors = input.asList.filterNotEmpty()
        .map {
            it.split(",", "and").mapNotNull {
                val words = it.split(" ", "-", ".").map { it.toUpperCase() }
                val element = words.firstOrNull { it in Element.values().map { it.toString() } }
                val type = words.firstOrNull { it in listOf("MICROCHIP", "GENERATOR") }
                if (element != null && type != null) {
                    if (type == "MICROCHIP") Device.Chip(Element.valueOf(element))
                    else if (type == "GENERATOR") Device.Generator(Element.valueOf(element))
                    else null
                } else {
                    null
                }
            }.toSet()
        }

    private fun calculateMinimalMoves(floors: List<Set<Device>>): Int {
        //https://www.reddit.com/r/adventofcode/comments/5hoia9/2016_day_11_solutions/db1v0hi?utm_source=share&utm_medium=web2x&context=3
        var moves = 0
        var itemsAcc = 0
        floors.dropLast(1).forEach {
            itemsAcc += it.size
            moves += 2 * (itemsAcc - 1) - 1

        }
        return moves
    }

    override fun part1(): Any {
        return calculateMinimalMoves(initialFloors)
    }

    /**
     * Finds a non-optimal path to solved state
     */
    private fun aStar(): Any {
        val facilityState = FacilityState(0, initialFloors, 0)

        val openList = mutableListOf(facilityState)
        val closedList = mutableListOf<FacilityState>()
        var found = false
        var iterator = 0
        while (openList.isNotEmpty() && !found) {
            println("Run $iterator")
            //val bestState = openList.best()
            val bestState = openList.maxByOrNull { it.score + (it.elevatorPosition + 1) }
            //val bestState = openList.filter { it.score == openList.maxOf { it.score } }.sortedByDescending { it.elevatorPosition }.sortedBy { it.stepsFromInitial }.firstOrNull()
            //val bestState = openList.maxByOrNull { it.score }
            openList.remove(bestState)
            bestState?.run {
                println("Best score is $score")
                val nextStates = nextStates
                nextStates.forEach { state ->
                    if (state !in closedList) {
                        if (state.solved) return state
                        if (state in openList) {
                            //println("State already on open list: $state")
                            val firstInOpenList = openList.first { it == state }
                            //println("Already in openlist: ${firstInOpenList.stepsFromInitial}, new: ${state.stepsFromInitial}")
                            if (state.stepsFromInitial < firstInOpenList.stepsFromInitial) {
                                println("Found a shorter path to this $state")
                                openList.remove(firstInOpenList)
                                openList.add(state)
                            }
                        } else {
                            //println("State on no list: $state")
                            openList.add(state)
                        }
                    }
                }
                closedList.add(this)
            }
            println("OpenList has ${openList.size} items, closed list ${closedList.size}")
            found = openList.any { it.solved }
            iterator++

        }

        return openList.firstOrNull { it.solved } ?: "null"
    }


    override fun part2(): Any {
        val floors = initialFloors.map { it.toMutableSet() }
        floors[0].addAll(setOf(Device.Chip(Element.ELERIUM), Device.Generator(Element.ELERIUM), Device.Chip(Element.DILITHIUM), Device.Generator(Element.DILITHIUM)))
        return calculateMinimalMoves(floors)
    }



    enum class Element(val abbr: String) {
        HYDROGEN("H"),
        LITHIUM("Li"),
        PLUTONIUM("Pu"),
        PROMETHIUM("Pm"),
        RUTHENIUM("Ru"),
        STRONTIUM("Sr"),
        THULIUM("Tm"),
        ELERIUM("E"),
        DILITHIUM("D")
    }

    sealed class Device(val element: Element) {
        class Chip(element: Element): Device(element) {
            val matchingGenerator: Generator get() = Generator(element)

            override fun toString(): String = "${element.abbr}M"

            override fun equals(other: Any?): Boolean {
                if  (this === other) return true
                if (other is Chip) {
                    return this.element == other.element
                } else {
                    return false
                }
            }
            override fun hashCode(): Int {
                return element.hashCode()
            }

        }

        class Generator(element: Element): Device(element) {
            val matchingChip: Chip get() = Chip(element)

            override fun toString(): String = "${element.abbr}G"

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other is Generator) {
                    return this.element == other.element
                } else {
                    return false
                }
            }
            override fun hashCode(): Int {
                return element.hashCode()
            }

        }

    }





    /**
     * Represents the facility state
     * @property stepsFromInitial The distance from initial state
     * @property floors A list of sets of devices
     * @property elevatorPosition Index starts with 0
     */
    class FacilityState(val stepsFromInitial: Int, val floors: List<Set<Device>>, val elevatorPosition: Int) {
        init {
            if (floors.any { !it.isSafe() }) throw IllegalArgumentException("Floors are not safe")
            if (elevatorPosition !in floors.indices) throw IllegalArgumentException("elevatorPosition not valid")
        }
        private val elementsInFacility = floors.flatMap { it.map { it.element } }.toSet()
        private val devicesInFacility = floors.flatten()
        private val highScore = 4 * devicesInFacility.score()
        val score = floors.mapIndexed { index, set ->
            (index + 1) * set.score()
        }.sum()
        val solved = highScore == score

        override fun toString(): String {
            val builder = StringBuilder()
            builder.appendLine("Distance from initial state: $stepsFromInitial")
            floors.mapIndexed { index, floor ->
                "F${index + 1} ${ if (index == elevatorPosition) "E" else "."} ${floor.toPrintableString()}"
            }.reversed().forEach {
                builder.appendLine(it)
            }
            return builder.toString()
        }

        private val lowestOccupiedFloor: Int = floors.indices.first { floors[it].isNotEmpty() }

        private val adjacentFloors: List<Int> = listOf(elevatorPosition - 1, elevatorPosition + 1).filter { it in floors.indices }.filter { it >= lowestOccupiedFloor }

        private val possiblePicks: Set<Set<Device>> = floors[elevatorPosition].possiblePicks()



        private val validPicks: Set<Set<Device>> = possiblePicks.filter { it.isSafe() && (floors[elevatorPosition] - it).isSafe() }.toSet()

        val nextStates: Set<FacilityState> by lazy {
            validPicks.flatMap { pick ->
                adjacentFloors.map { toIndex ->
                    move(pick, toIndex)
                }
            }.filterNotNull().toSet()
        }


        /**
         * Returns a facility state if move is valid, else null
         * @param devices The devices to move
         * @param toIndex Floor to add devices to
         */
        fun move(devices: Collection<Device>, toIndex: Int): FacilityState? {
            if (abs(toIndex-elevatorPosition) != 1) throw IllegalArgumentException("You can only move devices one floor at a time")
            val newFloors = floors.toMutableList()
            newFloors[elevatorPosition] = floors[elevatorPosition] - devices
            newFloors[toIndex] = floors[toIndex] + devices
            if (devices.isSafe() && newFloors[elevatorPosition].isSafe() && newFloors[toIndex].isSafe()) {
                try {
                    return FacilityState(stepsFromInitial + 1, newFloors, toIndex)
                } catch (e: IllegalArgumentException) {
                    println("Caught exception $e")
                    return null
                }
            } else {
                return null
            }

        }

        override fun equals(other: Any?): Boolean {
            if  (this === other) return true
            if (other is FacilityState) {
                return this.floors == other.floors && elevatorPosition == other.elevatorPosition
            } else {
                return false
            }
        }

        fun Collection<Device>.chips(): Collection<Device.Chip> = filterIsInstance<Device.Chip>()
        fun Collection<Device>.generators(): Collection<Device.Generator> = filterIsInstance<Device.Generator>()
        fun Collection<Device>.score(): Int = chips().size + 2 * generators().size
        fun Collection<Device>.isSafe(): Boolean {
            if (all { it is Device.Chip }) return true
            if (all { it is Device.Generator }) return true
            if (filterIsInstance<Device.Chip>().all { it.matchingGenerator in this }) return true
            return false
        }
        fun Collection<Device>.toPrintableString(): String {
            val builder = StringBuilder()
            elementsInFacility.sorted().forEach {
                val generator = Device.Generator(it)
                val generatorFound = contains(generator)
                val generatorString = if (generatorFound) generator.toString() else "."
                builder.append(generatorString.padStart(4, ' '))
                val chip = Device.Chip(it)
                val chipFound = contains(chip)
                val chipString = if (chipFound) chip.toString() else "."
                builder.append(chipString.padStart(4, ' '))

            }
            return builder.toString()

//            return map {
//                it.toString().padStart(4, ' ')
//            }.joinToString("")
        }
        fun Collection<Device>.possiblePicks(): Set<Set<Device>> {
            return when (size) {
                0 -> emptySet()
                1 -> map { setOf(it) }.toSet()
                else -> {
                    val mutableSet: MutableSet<Set<Device>> = mutableSetOf()
                    for (i in indices) {
                        for (j in i..indices.last) {
                            mutableSet.add(setOf(this.elementAt(i), this.elementAt(j)))
                        }
                    }
                    mutableSet
                }
            }
        }

        override fun hashCode(): Int {
            var result = floors.hashCode()
            result = 31 * result + elevatorPosition
            return result
        }


    }

}
