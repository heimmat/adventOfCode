package Year2016

class AssembunnyComputer(val program: List<Instruction>, private val debug: Boolean = false) {

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

    private val writableRegister = mutableMapOf<String,Int>()

    val register: Map<String,Int> get() = writableRegister

    fun initializeRegister(init: Map<String,Int>) {
        writableRegister += init
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
                writableRegister[instruction.y!!] = valueOf(instruction.x)
            }
            Instruction.Type.INC -> {
                writableRegister[instruction.x] = writableRegister[instruction.x]!! + 1
            }
            Instruction.Type.DEC -> {
                writableRegister[instruction.x] = writableRegister[instruction.x]!! - 1
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
        }.also { if (debug) {println(instruction); println("Next instruction at $it"); println("Register is $writableRegister"); println() } }

    }

    fun run() {
        var instructionPointer = 0
        while (instructionPointer in program.indices) {
            instructionPointer = executeInstruction(instructionPointer)
        }
    }

    private fun valueOf(string: String): Int {
        return if (string.length == 1) {
            if (string in "abcd") {
                writableRegister[string]!!
            } else {
                string.toInt()
            }
        } else {
            string.toInt()
        }.also { if (debug) println("valueOf $string is $it") }
    }

}