package util

fun <T> Collection<Collection<T>>.flipHorizontally(): Collection<Collection<T>> {
    return this.map {
        it.reversed()
    }
}

fun <T> Collection<Collection<T>>.flipVertically(): Collection<Collection<T>> = this.reversed()

fun <T> Collection<Collection<T>>.rotate(direction: Rotation, times: Int = 1): Collection<Collection<T>> {
    return when (times % 4) {
        0 -> this
        1 -> rotate(direction)
        2 -> map { it.reversed() }.reversed()
        3 -> {
            when (direction) {
                Rotation.LEFT -> rotateRight()
                Rotation.RIGHT -> rotateLeft()
            }
        }
        else -> throw IllegalStateException()
    }
}

fun <T> Collection<Collection<T>>.rotate(direction: Rotation): Collection<Collection<T>> {
    return when (direction) {
        Rotation.LEFT -> rotateLeft()
        Rotation.RIGHT -> rotateRight()
    }
}

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

fun <T> Collection<Collection<T>>.orientations(): List<Collection<Collection<T>>> {
    return listOf(
        this,
        rotate(Rotation.LEFT),
        rotate(Rotation.LEFT,2),
        rotate(Rotation.RIGHT),
        flipHorizontally(),
        flipVertically(),
        transpose(),
        rotate(Rotation.LEFT).flipHorizontally(),
        rotate(Rotation.LEFT,2).flipHorizontally(),
        rotate(Rotation.RIGHT).flipHorizontally(),
    )
}