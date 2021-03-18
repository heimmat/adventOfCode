package year2017

import Day

class Day08 : Day(2017,8) {

    private val instructions = input.asList.filterNotEmpty().map { Instruction.fromString(it) }

    private val stateRegister = StateRegister()
    private val instructionsMappedToHighestRegValAfterRun = instructions.map {
        stateRegister.runInstruction(it)
        stateRegister.values.maxOf { it }
    }

    override fun part1(): Any {
        return instructionsMappedToHighestRegValAfterRun.last()
    }

    override fun part2(): Any {
        return instructionsMappedToHighestRegValAfterRun.maxOf { it }
    }

    class StateRegister: Map<String,Int> {
        private val internalMap: MutableMap<String,Int> = mutableMapOf()

        override val entries: Set<Map.Entry<String, Int>> = internalMap.entries
        override val keys: Set<String> = internalMap.keys
        override val size: Int = internalMap.size
        override val values: Collection<Int> = internalMap.values
        override fun containsKey(key: String): Boolean = internalMap.containsKey(key)
        override fun containsValue(value: Int): Boolean = internalMap.containsValue(value)
        override fun get(key: String): Int = internalMap.getOrDefault(key, 0)
        override fun isEmpty(): Boolean = internalMap.isEmpty()

        fun runInstruction(instruction: Instruction): Unit {
            if (instruction.condition.evaluateInRegister(this)) {
                when (instruction.command) {
                    Instruction.Command.INC -> internalMap[instruction.register] = this[instruction.register] + instruction.value
                    Instruction.Command.DEC -> internalMap[instruction.register] = this[instruction.register] - instruction.value
                }
            }
        }
    }

    data class Instruction(val register: String, val command: Command, val value: Int, val condition: Condition) {

        companion object {
            fun fromString(string: String): Instruction {
                val split = string.split(" ")
                if (split.size == 7) {
                    return Instruction(split[0], Command.valueOf(split[1].toUpperCase()), split[2].toInt(), Condition(split[4], split[5], split[6].toInt()))
                } else {
                    throw IllegalArgumentException()
                }
            }
        }

        enum class Command {
            INC,
            DEC
        }
    }

    data class Condition(val registerName: String, val operator: Operator, val value: Int) {
        constructor(registerName: String, operator: String, value: Int) : this(
            registerName,
            Operator.fromRepresentation(operator),
            //Operator.values()[Operator.values().map { it.representation }.indexOf(operator)],
            value
        )

        fun evaluateInRegister(stateRegister: StateRegister): Boolean {
            return when (operator) {
                Operator.GT -> stateRegister[registerName] > value
                Operator.GE -> stateRegister[registerName] >= value
                Operator.LT -> stateRegister[registerName] < value
                Operator.LE -> stateRegister[registerName] <= value
                Operator.EQ -> stateRegister[registerName] == value
                Operator.NE -> stateRegister[registerName] != value
            }
        }

        enum class Operator(val representation: String) {
            GT(">"),
            LT("<"),
            GE(">="),
            LE("<="),
            EQ("=="),
            NE("!=");

            companion object {
                fun fromRepresentation(representation: String): Operator {
                    val representations = values().map { it.representation }
                    if (representation in representations) {
                        return values()[representations.indexOf(representation)]
                    } else {
                        throw IllegalArgumentException()
                    }
                }
            }
        }



    }
}
