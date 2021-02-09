import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.associate
import com.github.ajalt.clikt.parameters.options.option



class AdventOfCode: CliktCommand() {
    private val dayOption by option("-d","--days").associate()



    override fun run() {
        val dayProblems = dayOption.map {
            it.key.toInt() to it.value.split(',',';').map { it.toInt() }
        }.toMap()
        if (dayProblems.isEmpty()) {
            Year.supportedYears.forEach { y ->
                try {
                    val year = Year.fromInt(y)
                    Day.supportedDays.forEach { d ->
                        year.runDay(d)
                    }
                } catch (e: NotImplementedError) {
                    println("Year $y not implemented")
                }

            }
        } else {
            dayProblems.forEach { y, days ->
                val year = Year.fromInt(y)
                days.forEach { d ->
                    year.runDay(d)
                }
            }
        }
    }
}