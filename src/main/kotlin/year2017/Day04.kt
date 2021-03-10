package year2017

import Day
import util.permutate

class Day04 : Day(2017,4) {

    val phrases = input.asList.filterNotEmpty()

    override fun part1(): Any {
        return phrases.count { it.hasNoMultipleWords }
    }

    override fun part2(): Any {
        return phrases.count { it.hasNoAnagrams }
    }

    val String.hasNoMultipleWords: Boolean get() {
        val words = split(" ")
        val setOfWords = words.toSet()
        return words.size == setOfWords.size
    }

    val String.hasNoAnagrams: Boolean get() {
        val words = split(" ")
        return words.none {
            val otherWordsAnagrams = (words - it).flatMap { it.anagrams() }
            it in otherWordsAnagrams
        }
    }

    fun String.anagrams(): List<String> {
        return toList().permutate().map { it.joinToString("") }
    }
}
