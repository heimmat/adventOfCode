package util

fun <T> Collection<Collection<T>>.flipHorizontally(): Collection<Collection<T>> {
    return this.map {
        it.reversed()
    }
}

fun <T> Collection<Collection<T>>.flipVertically(): Collection<Collection<T>> = this.reversed()

fun <T> Collection<Collection<T>>.rotateRight(): Collection<Collection<T>> = transpose().map { it.reversed() }

fun <T> Collection<Collection<T>>.rotateLeft(): Collection<Collection<T>> = map { it.reversed() }.transpose()

fun <T> Collection<Collection<T>>.transpose(): Collection<Collection<T>> {
    if (size < 1) {
        throw IllegalArgumentException()
    } else if (any {it.size != first().size} ) {
        throw IllegalArgumentException()
    }

    val xIndices = first().indices
    val yIndices = indices

    val rows = mutableListOf<Collection<T>>()
    for (x in xIndices) {
        val columns = mutableListOf<T>()
        for (y in yIndices) {
            columns.add(elementAt(y).elementAt(x))
        }
        rows.add(columns)

    }
    return rows
}