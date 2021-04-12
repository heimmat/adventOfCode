package year2017

enum class Direction(val coordinateDiff: Pair<Int,Int>) {
    UP(0 to -1),
    RIGHT(1 to 0),
    DOWN(0 to 1),
    LEFT(-1 to 0);

    companion object {
        fun fromDiff(diff: Pair<Int, Int>): Direction {
            return when (diff) {
                0 to -1 -> UP
                0 to 1 -> DOWN
                1 to 0 -> RIGHT
                -1 to 0 -> LEFT
                else -> throw IllegalArgumentException()
            }
        }
    }
}