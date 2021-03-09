package Year2016

import Day

class Day25 : Day(2016,25) {


    private val program = input.asList.map {
        val split = it.split(" ")
        val instr = split.first().toUpperCase()
        val x = split[1]
        val y = if (split.size == 3) split.last() else null
        AssembunnyComputer.Instruction(AssembunnyComputer.Instruction.Type.valueOf(instr), x, y)
    }



    override fun part1(): Any {
        var prevClockSignal: Int = 1
        val listOfClockSignals = mutableListOf<Int>()
        var loopDetectedAt: Int = -1
        repeat(500) { repetition ->
            prevClockSignal = 1
            //println(repetition)
            val computer = AssembunnyComputer(program) {
                if (it !in 0..1) {
                    halt()
                    listOfClockSignals.clear()
                }
                if (it == prevClockSignal) {
                    halt()
                    listOfClockSignals.clear()
                }
                listOfClockSignals.add(it)
                //println(listOfClockSignals.joinToString(""))
                prevClockSignal = it
                if (listOfClockSignals.joinToString("").startsWith("1010101010")) {
                    halt()
                    loopDetectedAt = repetition
                }

            }
            computer.initializeRegister(
                listOf("a" to repetition).toMap()
            )
            computer.run()
            if (loopDetectedAt != -1) {
                return loopDetectedAt
            }
            //return computer.register["a"]!!
        }
        return loopDetectedAt
    }
}
