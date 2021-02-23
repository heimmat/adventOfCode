package Year2016

import Day

class Day12 : Day(2016,12) {

    private val DEBUG = false

    private val program: List<Instruction> = input.asList.map {
        val split = it.split(" ")
        val instr = split.first().toUpperCase()
        val x = split[1]
        val y = if (split.size == 3) split.last() else null
        Instruction(Instruction.Type.valueOf(instr), x, y)
    }

    private val register = mutableMapOf<String,Int>()

    private fun initializeRegisterForPart(part: Int = 1) {
        register["a"] = 0
        register["b"] = 0
        register["c"] = if (part == 1) 0 else 1
        register["d"] = 0
    }

    private fun runProgram() {
        var instructionPointer = 0
        while (instructionPointer in program.indices) {
            instructionPointer = executeInstruction(instructionPointer)
        }
    }

    override fun part1(): Any {
        initializeRegisterForPart()
        runProgram()
        return register["a"]!!
    }

    override fun part2(): Any {
        initializeRegisterForPart(2)
        runProgram()
        return register["a"]!!
    }

    /**
     * Runs a instruction from program and returns the next instructionPointer
     * @param instructionPointer The index of the instruction to run
     * @return The next instructionPointer
     */
    private fun executeInstruction(instructionPointer: Int): Int {
        val instruction = program[instructionPointer]
        when (instruction.type) {
            Instruction.Type.CPY -> {
                register[instruction.y!!] = valueOf(instruction.x)
            }
            Instruction.Type.INC -> {
                register[instruction.x] = register[instruction.x]!! + 1
            }
            Instruction.Type.DEC -> {
                register[instruction.x] = register[instruction.x]!! - 1
            }
        }

        return when (instruction.type) {
            Instruction.Type.JNZ -> {

                if (valueOf(instruction.x) != 0) {
                    instructionPointer + valueOf(instruction.y!!)
                } else {
                    instructionPointer + 1
                }
            }
            else -> {
                instructionPointer + 1
            }
        }.also { if (DEBUG) {println(instruction); println("Next instruction at $it"); println("Register is $register"); println() } }

    }

    private fun valueOf(string: String): Int {
        return if (string.length == 1) {
            if (string in "abcd") {
                register[string]!!
            } else {
                string.toInt()
            }
        } else {
            string.toInt()
        }.also { if (DEBUG) println("valueOf $string is $it") }
    }

    data class Instruction(val type: Type, val x: String, val y: String? = null) {
        enum class Type {
            CPY,
            INC,
            DEC,
            JNZ
        }

        override fun toString(): String {
            return "${type.toString().toLowerCase()} $x ${y ?: ""}"
        }
    }



}
