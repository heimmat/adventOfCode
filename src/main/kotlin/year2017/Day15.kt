package year2017

import Day

class Day15 : Day(2017,15) {

    val factors = input.asList.filterNotEmpty().map { it.substringAfterLast(" ").toInt() } //listOf(65,8921)//


    val andingValue = 65535L

    val generatorA = Generator(factors.first(),16807, 4)
    val generatorB = Generator(factors.last(),48271, 8)




    override fun part1(): Any {
        val comparedSequence = generateSequence {
            generatorA.next() and andingValue == generatorB.next() and andingValue
        }
        return comparedSequence.take(40000000).count { it }

    }

    override fun part2(): Any {

        val generatorA = generatorA.reset()
        val generatorB = generatorB.reset()

        val comparedSequence = generateSequence {
            generatorA.strictNext() and andingValue == generatorB.strictNext() and andingValue
        }
        return comparedSequence.take(5000000).count { it }
    }


    class Generator(val startValue: Int, val multiplyWith: Int, val multipleOf: Int) {
        private var currentValue = startValue.toLong()
        private val maxValue = 2147483647L

        val sequence = generateSequence { next() }
        val strictSequence = generateSequence { strictNext() }

        fun strictNext(): Long {
            var next = next()
            while (next % multipleOf != 0L) {
                next = next()
            }
            return next
        }

        fun next(): Long {
            currentValue = (currentValue * multiplyWith) % maxValue
            return currentValue
        }

        fun reset(): Generator = Generator(startValue, multiplyWith, multipleOf)
    }
}
