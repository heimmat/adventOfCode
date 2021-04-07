package year2017

import Day
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED

class Day18 : Day(2017,18) {

    val testProgram = """set a 1
                        add a 2
                        mul a a
                        mod a 5
                        snd a
                        set a 0
                        rcv a
                        jgz a -1
                        set a 1
                        jgz a -2"""
        .trimIndent()
        .split("\n")
        .map { SoundComputer.Command.parseString(it.trim()) }

    val program = input.asList.filterNotEmpty().map {
        SoundComputer.Command.parseString(it)
    }

    override fun part1(): Any {
        return SoundComputer().runUntilHalt(program)
    }

    @ExperimentalCoroutinesApi
    override fun part2(): Any = runBlocking {
//        val program = """snd 1
//            snd 2
//            snd p
//            rcv a
//            rcv b
//            rcv c
//            rcv d""".trimIndent().split("\n").map { SoundComputer.Command.parseString(it.trim()) }

        var counter = 0
        val queue0 = Channel<Long>(UNLIMITED)
        val queue1 = Channel<Long>(UNLIMITED)
        val computer0 = DuetComputer(0, queue0, queue1)
        val computer1 = DuetComputer(1, queue1, queue0) {
            counter++
        }
        var pointer0 = 0
        var pointer1 = 0



        val suspend1 = async {
            while (!(computer0.isWaiting && computer1.isWaiting) && pointer0 in program.indices) {
                pointer0 += computer0.executeCommand(program[pointer0])
            }
            return@async
        }
        val suspend2 = async {
            while (!(computer0.isWaiting && computer1.isWaiting) && pointer1 in program.indices) {
                pointer1 += computer1.executeCommand(program[pointer1])
            }
            return@async
        }

        try {
            awaitAll(suspend1, suspend2)
        } catch (e: TimeoutCancellationException) {
            //println(e.message)
        }

        counter
    }

    open class SoundComputer(protected val computerId: Long = 0) {

        protected val register = mutableMapOf<Char, Long>()

        private val soundBuffer = mutableListOf<Long>()

        private val recovered = mutableListOf<Long>()

        open fun runUntilHalt(program: List<Command>): Long {
            var pointer = 0
            while (recovered.isEmpty() && pointer in program.indices) {
                pointer += executeCommandBlocking(program[pointer])
            }
            return soundBuffer.last()
        }

        protected fun valueOf(string: String): Long {
            return if (string.length > 1) {
                //Assume it is a number
                string.toLong()
            } else {
                val char = string.first()
                if (char.isDigit()) {
                    string.toLong()
                } else {
                    register.getOrDefault(char, computerId)
                }
            }
        }

        /**
         * Returns the offset of the next command
         */
        open fun executeCommandBlocking(command: Command): Int {
            when (command.instruction) {
                Instruction.SND -> {
                    soundBuffer.add(valueOf(command.x))
                    return 1
                }
                Instruction.SET -> {
                    register[command.x.first()] = valueOf(command.y!!)
                    return 1
                }
                Instruction.ADD -> {
                    val prev = valueOf(command.x)
                    register[command.x.first()] = prev + valueOf(command.y!!)
                    return 1
                }
                Instruction.MUL -> {
                    val prev = valueOf(command.x)
                    register[command.x.first()] = prev * valueOf(command.y!!)
                    return 1
                }
                Instruction.MOD -> {
                    val prev = valueOf(command.x)
                    register[command.x.first()] = prev % valueOf(command.y!!)
                    return 1
                }
                Instruction.RCV -> {
                    if (valueOf(command.x) != 0L) {
                        recovered.add(soundBuffer.last())
                    }
                    return 1
                }
                Instruction.JGZ -> {
                    return if (valueOf(command.x) > 0L) {
                        valueOf(command.y!!).toInt()
                    } else {
                        1
                    }
                }

            }
        }


        data class Command(val instruction: Instruction, val x: String, val y: String? = null) {
            companion object {
                fun parseString(string: String): Command {
                    val split = string.split(" ")
                    if (split.size >= 2) {
                        return Command(Instruction.valueOf(split[0].toUpperCase()), split[1], split.getOrNull(2))
                    } else {
                        throw IllegalArgumentException()
                    }

                }
            }
        }

        enum class Instruction {
            SND,
            SET,
            ADD,
            MUL,
            MOD,
            RCV,
            JGZ
        }
    }

    class DuetComputer(programId: Long, private val sendingChannel: Channel<Long>, private val receivingChannel: Channel<Long>, private val onSend: (() -> Unit)? = null): SoundComputer(programId) {
        var isWaiting: Boolean = false
            private set

        suspend fun executeCommand(command: Command): Int {
            return when (command.instruction) {
                Instruction.SND -> {
                    val toSend = valueOf(command.x)
                    sendingChannel.send(toSend)
                    onSend?.invoke()
                    1
                }
                Instruction.RCV -> {
                    isWaiting = true
                    val received = withTimeout(1000) { receivingChannel.receive() }
                    isWaiting = false
                    register[command.x.first()] = received
                    1
                }
                else -> {
                    super.executeCommandBlocking(command)
                }
            }
        }
    }
}
