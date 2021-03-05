package Year2016

import Day

class Day12 : Day(2016,12) {

    private val DEBUG = false

    private val program: List<AssembunnyComputer.Instruction> = input.asList.map {
        val split = it.split(" ")
        val instr = split.first().toUpperCase()
        val x = split[1]
        val y = if (split.size == 3) split.last() else null
        AssembunnyComputer.Instruction(AssembunnyComputer.Instruction.Type.valueOf(instr), x, y)
    }

    private val computer = AssembunnyComputer(program, DEBUG)


    private fun initializeRegisterForPart(part: Int = 1) {
        val register = mutableMapOf<String,Int>()
        register["a"] = 0
        register["b"] = 0
        register["c"] = if (part == 1) 0 else 1
        register["d"] = 0
        computer.initializeRegister(register)
    }

    override fun part1(): Any {
        initializeRegisterForPart()
        computer.run()
        return computer.register["a"]!!
    }

    override fun part2(): Any {
        initializeRegisterForPart(2)
        computer.run()
        return computer.register["a"]!!
    }

}
