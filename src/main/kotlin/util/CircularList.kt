package util

class CircularList<T>(elements: Iterable<T>) : List<T> {
    private val internalList = elements.toList()
    override val size: Int = internalList.size
    override fun contains(element: T): Boolean = internalList.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = internalList.containsAll(elements)
    override fun get(index: Int): T = internalList.get(index % size)
    override fun indexOf(element: T): Int = internalList.indexOf(element)
    override fun isEmpty(): Boolean = internalList.isEmpty()
    override fun iterator(): Iterator<T> = internalList.iterator()
    override fun lastIndexOf(element: T): Int = internalList.lastIndexOf(element)
    override fun listIterator(): ListIterator<T> = internalList.listIterator()
    override fun listIterator(index: Int): ListIterator<T> = internalList.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<T> = internalList.subList(fromIndex, toIndex)
    override fun toString(): String = internalList.toString()
}