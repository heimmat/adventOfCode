package year2018

import Day

class Day07(debug: Boolean = false) : Day(2018,7, debug) {
    val testInput = """
        Step C must be finished before step A can begin.
        Step C must be finished before step F can begin.
        Step A must be finished before step B can begin.
        Step A must be finished before step D can begin.
        Step B must be finished before step E can begin.
        Step D must be finished before step E can begin.
        Step F must be finished before step E can begin.""".trimIndent().split("\n")
    val inputSimplified = (if (debug) testInput else input.asList).map {
        val split = it.split(" ")
        split[7].first() to split[1].first() }


    val taskDependencyGraph = inputSimplified.groupBy { it.first }.map { it.key to it.value.map { it.second }.toSet() }.toMap()
    //val tasksWithoutPredecessor = inputSimplified.toMap().values.filter { it !in inputSimplified.toMap().keys }
    val highestChar = if (debug) 'F' else 'Z'
    val charRange = 'A'..highestChar
    val tasksWithoutPredecessor = charRange.filter { it !in inputSimplified.toMap().keys }

    var finishedTasks = mutableSetOf<Char>()
    val tasksWithFinishedPredecessor: () -> Set<Char> = {
        taskDependencyGraph.filter { it.value.all { it in finishedTasks } }.keys.filterNot { it in finishedTasks }.toSet()
    }
    val availableTasks: () -> Set<Char> = {
        (tasksWithFinishedPredecessor() + tasksWithoutPredecessor).filter { it !in finishedTasks }.toSet()
    }
    val remainingTasks: () -> Set<Char> = {
        charRange.filterNot { it in finishedTasks || it in availableTasks() }.toSet()
    }


    override fun part1(): Any {
        //find task not in requirements
//        println("tasksWithoutPredecessor: $tasksWithoutPredecessor")
        val firstTask = tasksWithoutPredecessor.sorted().first()
//        println(taskDependencyGraph)
//        println(taskDependencyGraph.size)
        var nextTask = firstTask
        while (finishedTasks.size < charRange.count() - 1 && availableTasks().isNotEmpty()) {
//            println("nextTask: $nextTask")
            finishedTasks.add(nextTask)
//            println("finishedTasks: $finishedTasks")
            nextTask = availableTasks().sorted().first()
//            println("tasksWithFinishedPredecessor: ${tasksWithFinishedPredecessor()}")
//            println("availableTasks: ${availableTasks()}")
//            println("remainingTasks: ${remainingTasks()}")
//            println("$taskDependencyGraph\n")
        }
        finishedTasks.add(nextTask)
//        println(finishedTasks.size)
        return finishedTasks.joinToString("")

    }

    override fun part2(): Any {
        finishedTasks = mutableSetOf()
        val baseDuration = if (debug) 1 else 61
        val workerCount = if (debug) 2 else 5
        fun timeForTask(task: Char): Int {
            return baseDuration + charRange.indexOf(task)
        }
        val workers = MutableList(workerCount) {
            '.' to -1
        }



        fun freeWorker(): Int {
            return workers.indexOfFirst { it.first == '.' }
        }
        fun tasksInProgress(): Set<Char> {
            return workers.map { it.first }.filterNot { it == '.' }.toSet()
        }

        val nextTask: () -> Char? = {
            availableTasks().filterNot { it in tasksInProgress() }.sorted().firstOrNull()
        }
        var tick = 0
        val whileCondition: () -> Boolean = { finishedTasks.size < charRange.count() }
        while ( whileCondition()/*&& availableTasks().isNotEmpty()*/) {
            if (debug) println("Tick $tick")
            if (debug) println("Workers before replacement $workers")
            workers.replaceAll {
                if (tick - it.second == timeForTask(it.first)) {
                    finishedTasks.add(it.first)
                    '.' to -1
                } else {
                    it
                }

            }
            if (debug) println("Workers after replacement $workers")
            if (debug) println("Finished tasks $finishedTasks")
            while (freeWorker() != -1 && nextTask() != null) {
                val indexOfFreeWorker = freeWorker()
                if (debug) println("Found free worker $indexOfFreeWorker")
                workers[indexOfFreeWorker] = nextTask()!! to tick
                if (debug) println("assigned worker $indexOfFreeWorker task ${workers[indexOfFreeWorker]}")
            }
            tick++
            if (debug) println("while evaluates to ${whileCondition()}")
        }


        return tick
    }


}
