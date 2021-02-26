package util

fun String.firstRepeatingChar(times: Int): Char? {
    return windowed(times).map { window ->
        if (window.all { it == window.first() }) window.first() else null
    }.firstOrNull { it != null }

}
fun String.containsRepeatingChar(char: Char, times: Int): Boolean = contains("$char".padStart(times, char))