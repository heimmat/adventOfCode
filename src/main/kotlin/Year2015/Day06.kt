package Year2015

import Day

class Day06 : Day(2015,6) {
    val listOfInstruction = input.asList.filter { it != "" }.map { Instruction.fromString(it) }

    val mapOfLights = mutableMapOf<Pair<Int,Int>, Boolean>()
    val mapOfLightLevels = mutableMapOf<Pair<Int,Int>, Int>()

    override fun part1(): String {
        listOfInstruction.forEach {
            for (y in it.lowerCorner.second..it.upperCorner.second) {
                for (x in it.lowerCorner.first..it.upperCorner.first) {
                    val coordinate = Pair(x,y)
                    when (it.command) {
                        Command.OFF -> setLight(coordinate,false)
                        Command.ON -> setLight(coordinate,true)
                        Command.TOGGLE -> toggleLight(coordinate)
                    }
                }
            }
        }
        return mapOfLights.count { it.value }.toString()
    }

    override fun part2(): String {
        listOfInstruction.forEach {
            for (y in it.lowerCorner.second..it.upperCorner.second) {
                for (x in it.lowerCorner.first..it.upperCorner.first) {
                    val coordinate = Pair(x,y)
                    val currentLevel = mapOfLightLevels.getOrDefault(coordinate, 0)
                    when (it.command) {
                        Command.OFF -> {
                            if (currentLevel != 0) {
                                mapOfLightLevels[coordinate] = currentLevel-1
                            }
                        }
                        Command.ON -> mapOfLightLevels[coordinate] = currentLevel + 1
                        Command.TOGGLE -> mapOfLightLevels[coordinate] = currentLevel + 2
                    }
                }
            }
        }
        return mapOfLightLevels.values.sumBy { it }.toString()
    }

    private fun toggleLight(coordinate: Pair<Int,Int>) {
        val currentState = mapOfLights.getOrDefault(coordinate, false)
        setLight(coordinate, !currentState)
    }

    private fun setLight(coordinate: Pair<Int, Int>, state: Boolean) {
        mapOfLights[coordinate] = state
    }

    data class Instruction(val command: Command, val lowerCorner: Pair<Int, Int>, val upperCorner: Pair<Int,Int>) {
        companion object {
            fun fromString(string: String): Instruction {
                val split = string.replace("turn ", "").replace("through ", "").split(" ").filter { it != "" }
                val command = Command.valueOf(split.first().toUpperCase())
                val lowerCorner = split[1].toPair()
                val upperCorner = split[2].toPair()
                return Instruction(command, lowerCorner, upperCorner)
            }

            private fun String.toPair(): Pair<Int,Int> {
                val split = split(",")
                return Pair(split[0].toInt(), split[1].toInt())
            }
        }


    }

    enum class Command {
        TOGGLE,
        ON,
        OFF
    }

}
