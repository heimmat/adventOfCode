package Year2015

import Day

class Day15 : Day(2015,15) {
    private val testInput = """
        Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
        Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
    """.trimIndent()

    private val ingredients: Map<String, Map<String,Int>> = input.asList.filterNotEmpty().map {
        val (name, ingredients) = it.split(": ")
        name to ingredients.split(", ").map {
            val (ingrName, ingrDiff) = it.split(" ")
            ingrName to ingrDiff.toInt()
        }.toMap()
    }.toMap()

    private val properties = ingredients.values.fold(setOf<String>()) { acc, map ->
        acc + map.keys
    }

    private val combinations: List<Map<String,Int>> by lazy {
        getPossibleIngredientCounts()
    }

    override fun part1(): String {
        return combinations.maxOf {
            scoreOf(it)
        }.toString()
    }

    override fun part2(): String {
        val matchingCombinations = combinations.filter { calorieTotal(it) == 500L }
        val highestScore = matchingCombinations.maxOf { scoreOf(it) }
        return highestScore.toString()
    }

    private fun getPossibleIngredientCounts(): List<Map<String,Int>> {
        val mutableList = mutableListOf<Map<String,Int>>()
        for (countSugar in 1..100) {
            for (countSprinkles in 1..100-countSugar) {
                for (countCandy in 1..100-countSugar-countSprinkles) {
                    for (countChoc in 1..100-countSugar-countSprinkles-countCandy) {
                        if (countSugar + countSprinkles + countCandy + countChoc == 100) {
                            mutableList.add(mapOf(
                                "Sugar" to countSugar,
                                "Sprinkles" to countSprinkles,
                                "Candy" to countCandy,
                                "Chocolate" to countChoc
                            ))
                        }
                    }
                }
            }
        }
        return mutableList
    }

    private fun scoreOf(ingredientCount: Map<String,Int>): Long {
        val relevantProperties = properties - "calories"
        val scorePerProperty = relevantProperties.map { prop ->
            ingredientCount.map {
                ingredients[it.key]!![prop]!! * it.value
            }.sum()
        }
        return scorePerProperty.fold(1L) { acc, i ->
            val factor = if (i >= 0) i else 0
            acc * factor
        }
    }

    private fun calorieTotal(ingredientCount: Map<String, Int>): Long {
        return ingredientCount.map {
            ingredients[it.key]!!["calories"]!! * it.value.toLong()
        }.sum()
    }
}
