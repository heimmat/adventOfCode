package Year2015

import Day

class Day08 : Day(2015,8) {
    private val santasList = input.asList.filterNotEmpty()

    private val test = listOf(
            """""""",
            """"abc"""",
            """"aaa\"aaa"""",
            """"\x27""""
    )


    override fun part1(): String {
        return santasList.sumBy {
            it.codeRepresentationSize-it.memoryRepresentationSize
        }.toString()
    }

    override fun part2(): String {
        return santasList.sumBy {
            it.encodedRepresentationSize-it.codeRepresentationSize
        }.toString()
    }

    private val String.encodedRepresentationSize: Int get() {
        return encodedRepresentation.length
    }

    private val String.encodedRepresentation: String get() {
        val regex = Regex("""[\\\"]""")
        return """"${
            regex.replace(this) {
                """\${it.value}"""
            }
        }""""
    }
    
    private val String.codeRepresentationSize: Int get() = length

    private val String.memoryRepresentation: String get() =
        drop(1).dropLast(1).replace("""\\""", """\""").replace("""\"""", "\"").replaceHexWithChar()

    private val String.memoryRepresentationSize: Int get() = memoryRepresentation.length

    private fun String.replaceHexWithChar(): String {
        val regex = Regex("""\\x([\da-f]{2})""")
        return regex.replace(this) {
            it.groupValues.last().toInt(16).toChar().toString()
        }
    }
}
