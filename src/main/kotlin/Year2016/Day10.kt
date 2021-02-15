package Year2016

import Day

class Day10 : Day(2016,10) {

    private val bots = input.asList
        .filterNotEmpty()
        .filter { it.startsWith("bot ") }
        .map {
            val id = it.substringBefore(" gives").substringAfter("bot ").toInt()
            val lowStr = it.substringAfter("low to ").substringBefore(" and")
            val (lowType,lowId) = lowStr.split(" ")
            val highStr = it.substringAfter("high to ")
            val (highType, highId) = highStr.split(" ")
            id to Bot(id, lowId.toInt(), Output.Type.valueOf(lowType.toUpperCase()), highId.toInt(), Output.Type.valueOf(highType.toUpperCase()))
        }.toMap()
    val outputs: MutableMap<Int, Int> = mutableMapOf()
    val values = input.asList.filterNotEmpty().filter { it.startsWith("value ") }.map {
        val value = it.substringAfter("value ").substringBefore(" goes").toInt()
        val bot = it.substringAfter("bot ").toInt()
        value to bot
    }
    init {
        values.forEach {
            bots[it.second]!!.isHanded(it.first)
        }
    }



    open class Output(val id: Int) {
        protected val inHand: MutableList<Int> = mutableListOf()
        open fun isHanded(value: Int) {
            inHand.add(value)
        }

        enum class Type {
            OUTPUT,
            BOT
        }
    }
    inner class Bot(id: Int, val lowTo: Int, val lowType: Type, val highTo: Int, val highType: Type): Output(id) {

        val isChosen: Boolean get() = inHand.containsAll(listOf(61,17))

        override fun isHanded(value: Int) {
            super.isHanded(value)
            if (inHand.size == 2) {
                val lowVal = inHand.sorted().first()
                val highVal = inHand.sorted().last()
                //hand low to lowTo
                when (lowType) {
                    Type.BOT -> {
                        bots[lowTo]!!.isHanded(lowVal)
                    }
                    Type.OUTPUT -> {
                        outputs[lowTo] = lowVal
                    }
                }
                when (highType) {
                    Type.BOT -> {
                        bots[highTo]!!.isHanded(highVal)
                    }
                    Type.OUTPUT -> {
                        outputs[highTo] = highVal
                    }
                }
            }
        }

        override fun toString(): String {
            return "Bot $id: low to $lowType $lowTo, high to $highType $highTo, $inHand\n"
        }
    }

    override fun part1(): Any {
        return bots.values.find { it.isChosen }?.id ?: "null"
    }

    override fun part2(): Any {
        return outputs[0]!! * outputs[1]!! * outputs[2]!!
    }

}
