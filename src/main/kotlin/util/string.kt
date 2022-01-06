package util

fun String.firstRepeatingChar(times: Int): Char? {
    return windowed(times).map { window ->
        if (window.all { it == window.first() }) window.first() else null
    }.firstOrNull { it != null }

}
fun String.containsRepeatingChar(char: Char, times: Int): Boolean = contains("$char".padStart(times, char))

tailrec fun String.rotateLeft(n: Int = 1): String {
    val rotateOnce = "${this.drop(1)}${this.first()}"
    if (n == 0) {
        return this
    } else if (n == 1) {
        return rotateOnce
    } else {
        return rotateOnce.rotateLeft(n-1)
    }
}

tailrec fun String.rotateRight(n: Int = 1): String {
    val rotateOnce = "${this.last()}${this.dropLast(1)}"
    if (n == 0) {
        return this
    } else if (n == 1) {
        return rotateOnce
    } else {
        return rotateOnce.rotateRight(n-1)
    }
}

fun String.convertFromHexToBinary(): String = this.map {
    "$it".toLong(16).toString(2).padStart(4, '0')
}.joinToString("")