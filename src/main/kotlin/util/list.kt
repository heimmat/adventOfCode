package util

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