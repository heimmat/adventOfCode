package year2017

import Day

class Day07 : Day(2017,7) {
    private val nodes = input.asList.filterNotEmpty().map {
        val lh = it.substringBefore(" -> ")
        val name = lh.substringBefore(" ")
        val weight = lh.substringAfter("(").substringBefore(")").toInt()
        val children = it.substringAfter(" -> ", "").split(", ")
        name to Node(name, weight, children)
    }.toMap()

    private val rootNode by lazy { nodes.rootNode() }

    override fun part1(): Any {
        return rootNode.key
    }

    override fun part2(): Any {
        val (unbalancedNode, targetWeight) = findUnbalancedNode(rootNode.value.toTreeNode(nodes))
        val diff = unbalancedNode.totalWeight - targetWeight
        return unbalancedNode.weight - diff
    }

    private fun Map<String,Node>.rootNode(): Map.Entry<String,Node> {
        return this.filter { node ->
            this.values.map { it.children }.none { node.key in it }
        }.entries.first()
    }

    data class Node(val name: String, val weight: Int, val children: List<String> = emptyList()) {
        fun toTreeNode(mapOfNodes: Map<String, Node>): TreeNode {
            return TreeNode(name, weight, children.mapNotNull {
                mapOfNodes[it]?.toTreeNode(mapOfNodes)
            })
        }
    }


    private fun findUnbalancedNode(root: TreeNode): Pair<TreeNode, Int> {
        var next = root
        var childrenByWeight: Map<Int, List<TreeNode>> = emptyMap()
        while (!next.childrenAreBalanced) {
            childrenByWeight = next.children.groupBy { it.totalWeight }
            val oddChild = childrenByWeight.entries.first { it.value.size == 1 }.value.first()
            next = oddChild
        }
        return next to childrenByWeight.maxByOrNull { it.value.size }!!.key

    }

    data class TreeNode(val name: String, val weight: Int, val children: List<TreeNode>) {
        private val weightOfChildren by lazy {  weightOfChildren() }
        private fun weightOfChildren(): Int = children.sumBy { it.totalWeight }
        val totalWeight: Int by lazy { weight + weightOfChildren  }


        val childrenAreBalanced: Boolean by lazy { children.all { it.totalWeight == weightOfChildren/children.size} }



    }


}
