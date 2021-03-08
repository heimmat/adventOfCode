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
        val onClock: AssembunnyComputer.(Int) -> Unit = {
            if (it !in 0..1) {
                halt()
            }
            if (it == prevClockSignal) {
                halt()
            }
            prevClockSignal = it


        }
        repeat(500) {
            prevClockSignal = 1
            println(it)
            val computer = AssembunnyComputer(program, onClockSignal = onClock)
            computer.initializeRegister(
                listOf("a" to it).toMap()
            )
            computer.run()
            //return computer.register["a"]!!
        }
        return ""
    }
}
