package util

class Stack<T>: Collection<T> {

    private val mutableList: MutableList<T> = mutableListOf()
    override val size: Int get() = mutableList.size
    override fun contains(element: T): Boolean = mutableList.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = mutableList.containsAll(elements)
    override fun isEmpty(): Boolean = mutableList.isEmpty()
    override fun iterator(): Iterator<T> = mutableList.iterator()

    fun push(item: T) = mutableList.add(size, item)
    fun pop(): T? = if (mutableList.isNotEmpty()) mutableList.removeLast() else null
    fun peek(): T? = if (this.isNotEmpty()) mutableList.last() else null
    fun hasMore() = mutableList.isNotEmpty()
    fun toList(): List<T> = mutableList.toList()

    override fun toString(): String {
        return mutableList.toString()
    }
}