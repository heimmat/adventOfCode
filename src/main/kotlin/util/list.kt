package util

import kotlin.math.pow

//Inspired by https://www.geeksforgeeks.org/merging-intervals/
fun <T : Comparable<T>> List<ClosedRange<T>>.merge(): List<ClosedRange<T>> {
    if (isEmpty()) {
        return this
    } else {
        val sorted = this.sortedBy { it.start }
        val stack: Stack<ClosedRange<T>> = Stack()
        sorted.forEach {
            if (stack.isEmpty()) {
                stack.push(it)
            } else {
                val top = stack.peek()!!
                if (top.endInclusive < it.start) {
                    stack.push(it)
                } else if (top.endInclusive < it.endInclusive) {
                    stack.pop()
                    stack.push(top.start..it.endInclusive)
                }
            }
        }
        return stack.toList()

    }
}

fun <T> List<T>.permutate(): List<List<T>> {
    if (isEmpty()) {
        return emptyList()
    } else if (size == 1) {
        return listOf(this)
    } else {
        return flatMap {
            (this - it).permutate().map { perm ->
                perm + it
            }
        }
    }

}

tailrec fun List<Number>.geothmeticMeandian(): Double {
    val mean = mean()
    val median = median().toDouble()
    val geomMean = geometricMean()
    if (mean == median && median == geomMean) {
        return mean
    } else {
        return listOf(mean, geomMean, median).geothmeticMeandian()
    }
}


fun List<Number>.sum(): Double = fold(0.0) { acc: Double, number ->
    acc + number.toDouble()
}


fun List<Number>.mean(): Double {
    return sum()/size
}

fun List<Number>.median(): Number {
    return this.sortedBy { it.toDouble() }[size/2]
}

fun List<Number>.geometricMean(): Double {
    val product = fold(1.0) { acc, number ->
        acc * number.toDouble()
    }
    return product.pow(1.0/size)
}