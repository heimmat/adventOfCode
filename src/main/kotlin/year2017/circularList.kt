package year2017

import util.CircularList
import util.toCircularList

fun CircularList<Int>.pinchAndTwist(currentPosition: Int, length: Int): CircularList<Int> {
    val range = currentPosition until currentPosition + length
    val reversedRange: List<Int> = range.toList().reversed()
    val reversedOrder = range
        .mapIndexed { index, it ->
            it % size to reversedRange[index]%size
        }.toMap()
    return mapIndexed { index, i ->
        if (index in reversedOrder.keys) {
            this[reversedOrder[index]!!]
        } else {
            i
        }
    }.toCircularList()
}