import Year2015.Year2015

open class Year(val year: Int) {
    companion object {
        val supportedYears = 2015..2020
        fun fromInt(year: Int): Year {
            if (year in supportedYears) {
                return when (year) {
                    2015 -> Year2015()
                    else -> TODO()
                }
            } else {
                TODO()
            }
        }
    }

    open val days: Map<Int,Day> = mapOf()

    fun runDay(dayOfYear: Int) {
        println("Year $year Day $dayOfYear")
        val day = days[dayOfYear]
        if (day != null) {
            try {
                println("Part 1: ${day.part1()}")
            } catch (e: NotImplementedError) {
                println("Part 1 not implemented")
            }
            try {
                println("Part 2: ${day.part2()}")
            } catch (e: NotImplementedError) {
                println("Part 2 not implemented")
            }

        } else {
            println("Day $dayOfYear not implemented")
        }


    }
}