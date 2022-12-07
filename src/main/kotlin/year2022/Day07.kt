package year2022

import Day

class Day07(debug: Boolean = false): Day(2022,7, debug) {

    private val testInput = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()
    private val fileTree = if (debug) testInput else input.asString

    override fun part1(): Any {
        return fileTree.parseFileTree().allSubdirsAndSelf().filter{ it.size <= 100000 }.map { it.size }.sum()
    }

    override fun part2(): Any {
        val fileTree = fileTree.parseFileTree()
        val usedSpace = fileTree.size
        val unusedSpace = 70000000 - usedSpace
        val needToDelete = 30000000 - unusedSpace
        if (debug) println("usedSpace $usedSpace, needToDelete: $needToDelete")
        if (debug) println(fileTree.allSubdirsAndSelf().map { it.name to it.size }.sortedBy { it.second })
        return fileTree.allSubdirsAndSelf().filter { it.size >= needToDelete }.sortedBy { it.size }.map { it.size }.first()
    }

    fun String.parseFileTree(): Dir {
        val split = this.split("\n")
        val rootDir = Dir(split.first().substringAfter("$ cd "), null)
        var currentDir: Dir = rootDir
        var ls = false
        for (line in split.drop(1).filterNotEmpty()) {
            if (line.startsWith("$")) {
                val line = line.drop(2)
                if (line.startsWith("cd ")) {
                    ls = false
                    val dirName = line.drop(3)
                    if (dirName == "..") {
                        currentDir = currentDir.parent!!
                    } else {
                        currentDir = currentDir.findDir(dirName)!!
                    }
                } else if (line.startsWith("ls")) {
                    ls = true
                }
            }
            else if (ls) {
                if (line.startsWith("dir")) {
                    val dirName = line.substringAfter("dir ")
                    currentDir.addChild(Dir(dirName, currentDir))
                } else {
                    val size = line.substringBefore(" ").toInt()
                    val name = line.substringAfter(" ")
                    currentDir.addChild(File(name, size, currentDir))
                }
            }
            if (debug) println("After reading line \"$line\":\ncurrentDir=${currentDir.name}, has ${currentDir.children().size} children\n\$ls is $ls\n")
        }

        return rootDir
    }

    class Dir(override val name: String, override val parent: Dir?, initialChildren: List<Dir> = listOf()): Node {
        private val children: MutableList<Node> = initialChildren.toMutableList()
        override val size: Int
            get() = children.sumOf { it.size }

        fun addChild(node: Node) {
            children.add(node)
        }

        fun children(): List<Node> = children

        fun allSubdirs(): List<Dir> = children.filter { it is Dir }.flatMap { (it as Dir).allSubdirs() + it }

        fun allSubdirsAndSelf(): List<Dir> = allSubdirs() + this

        fun findDir(name: String): Dir? {
            return children.find { it.name == name && it is Dir } as Dir?
        }

        override fun toString(): String {
            return print(0)
        }

        override fun print(indent: Int): String {
            val sb = StringBuilder()
            repeat(indent) {
                sb.append("  ")
            }
            sb.append("- $name (dir)\n${children.print(indent + 1)}")
            return sb.toString()
        }

        fun List<Node>.print(indent: Int): String {
            val sb = StringBuilder()
            for (node in this) {
                repeat(indent) {
                    sb.append("  ")
                }
                sb.append("${node.print(indent + 1)}\n")
            }
            return sb.toString()
        }
    }


    interface Node {
        val name: String
        val size: Int
        val parent: Dir?
        fun print(indent: Int): String
    }

    data class File(override val name: String, override val size: Int, override val parent: Dir): Node {
        override fun print(indent: Int): String {
            val sb = StringBuilder()
            repeat(indent) {
                sb.append("  ")
            }
            sb.append("- $name (file, size=$size)")
            return sb.toString()
        }

        override fun toString(): String {
            return print(0)
        }
    }



}