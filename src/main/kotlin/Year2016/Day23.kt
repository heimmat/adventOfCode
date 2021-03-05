package Year2016

import Day

class Day23 : Day(2016,23) {

    val program = input.asList.map {
        val split = it.split(" ")
        val instr = split.first().toUpperCase()
        val x = split[1]
        val y = if (split.size == 3) split.last() else null
        AssembunnyComputer.Instruction(AssembunnyComputer.Instruction.Type.valueOf(instr), x, y)
    }



    override fun part1(): Any {
        val computer = AssembunnyComputer(program)
        computer.initializeRegister(mapOf(
            "a" to 7
        ))
        computer.run()
        return computer.register["a"]!!
    }

    override fun part2(): Any {
        val computer = AssembunnyComputer(program)
        computer.initializeRegister(mapOf(
            "a" to 12
        ))
        computer.run()
        return computer.register["a"]!!
    }

}
