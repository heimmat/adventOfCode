package year2018

import Day

class Day07 : Day(2018,7) {
    val testInput = """
        Step C must be finished before step A can begin.
        Step C must be finished before step F can begin.
        Step A must be finished before step B can begin.
        Step A must be finished before step D can begin.
        Step B must be finished before step E can begin.
        Step D must be finished before step E can begin.
        Step F must be finished before step E can begin.""".trimIndent().split("\n")
    val inputSimplified = input.asList.map {
        val split = it.split(" ")
        split[7].first() to split[1].first() }


    val taskDependencyGraph = inputSimplified.groupBy { it.first }.map { it.key to it.value.map { it.second }.toSet() }.toMap()
    //val tasksWithoutPredecessor = inputSimplified.toMap().values.filter { it !in inputSimplified.toMap().keys }
    val highestChar = 'Z'
    val charRange = 'A'..highestChar
    val tasksWithoutPredecessor = charRange.filter { it !in inputSimplified.toMap().keys }

    override fun part1(): Any {
        //find task not in requirements
        println("tasksWithoutPredecessor: $tasksWithoutPredecessor")
        val finishedTasks = mutableSetOf<Char>()
        val tasksWithFinishedPredecessor: () -> Set<Char> = {
            taskDependencyGraph.filter { it.value.all { it in finishedTasks } }.keys.filterNot { it in finishedTasks }.toSet()
        }
        val availableTasks: () -> Set<Char> = {
            (tasksWithFinishedPredecessor() + tasksWithoutPredecessor).filter { it !in finishedTasks }.toSet()
        }
        val remainingTasks: () -> Set<Char> = {
            charRange.filterNot { it in finishedTasks || it in availableTasks() }.toSet()
        }
        val firstTask = tasksWithoutPredecessor.sorted().first()


        println(taskDependencyGraph)
        println(taskDependencyGraph.size)

        var nextTask = firstTask

        while (finishedTasks.size < charRange.count() - 1 && availableTasks().isNotEmpty()) {
            println("nextTask: $nextTask")
            finishedTasks.add(nextTask)
            println("finishedTasks: $finishedTasks")
            nextTask = availableTasks().sorted().first()
            println("tasksWithFinishedPredecessor: ${tasksWithFinishedPredecessor()}")
            println("availableTasks: ${availableTasks()}")
            println("remainingTasks: ${remainingTasks()}")
            println("$taskDependencyGraph\n")
        }
        finishedTasks.add(nextTask)
        println(finishedTasks.size)
        return finishedTasks.joinToString("")

    }

}
