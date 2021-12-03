open class Day(val year: Int, val day: Int, val debug: Boolean = false) {

    companion object {
        val supportedDays = 1..25
    }

    val input = Input(year,day)
    //protected val inputAsString by lazy { input.asString() }
    //protected val inputAsList by lazy { input.asList() }
    open fun part1(): Any = TODO()

    open fun part2(): Any = TODO()

    fun List<String>.filterNotEmpty(): List<String> = filter { it != "" }
    fun String.filterNotWhitespace(): String = filterNot { it.isWhitespace() }
}