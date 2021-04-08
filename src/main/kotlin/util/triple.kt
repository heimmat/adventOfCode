package util

operator fun Triple<Int,Int,Int>.plus(that: Triple<Int,Int,Int>): Triple<Int,Int,Int> {
    return Triple(first + that.first, second + that.second, third + that.third)
}

operator fun Triple<Int,Int,Int>.times(that: Int): Triple<Int,Int,Int> {
    return Triple(first * that, second * that, third * that)
}
