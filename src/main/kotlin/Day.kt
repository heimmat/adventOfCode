open class Day(val year: Int, val day: Int) {

    companion object {
        val supportedDays = 1..25
    }

    protected val input = Input(year,day)
    //protected val inputAsString by lazy { input.asString() }
    //protected val inputAsList by lazy { input.asList() }

    open fun part1(): Any = TODO()

    open fun part2(): Any = TODO()

    fun List<String>.filterNotEmpty(): List<String> = filter { it != "" }
}