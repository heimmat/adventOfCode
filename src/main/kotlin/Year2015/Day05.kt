package Year2015

import Day

class Day05 : Day(2015,5) {
    val listOfWords = input.asList.filter { it != "" }

    override fun part1(): String {
        return listOfWords.count { it.hasRecurringLetter && it.hasNoForbiddenCombinations && it.hasEnoughVowels }.toString()
    }

    override fun part2(): String {
        return listOfWords.count { it.hasPairTwice && it.hasRepeatWithSingleLetterBetween }.toString()
    }

    private val String.hasEnoughVowels: Boolean get() {
        return this.count { it in "aeiou" } >= 3
    }

    private val String.hasRecurringLetter: Boolean get() {
        return this.contains(Regex("([a-z])\\1"))
    }

    private val String.hasNoForbiddenCombinations: Boolean get() {
        return !(this.contains("ab") || this.contains("cd") || this.contains("pq") || this.contains("xy"))
    }

    private val String.hasPairTwice: Boolean get() {
        for (index in 1..this.lastIndex) {
            val pair = this.substring(index-1, index+1)
            if (this.substring(index+1).contains(pair)) {
                return true
            }
        }
        return false
    }

    private val String.hasRepeatWithSingleLetterBetween: Boolean get() = contains(Regex("([a-z])\\w\\1"))


}
