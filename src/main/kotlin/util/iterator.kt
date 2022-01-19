package util

fun <T> Iterator<T>.next(n: Int): List<T> {
    return (1..n).map {
        next()
    }
}