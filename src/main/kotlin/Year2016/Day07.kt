package Year2016

import Day

class Day07 : Day(2016,7) {
    private fun String.supportsTls(): Boolean {
        return if (hypernetSubstrings().any { it.hasAbba() }) {
            false
        } else {
            hasAbba()
        }
    }

    private fun String.hypernetSubstrings(): List<String> {
        val bracketRegex = Regex("""(?<=\[)\w+(?=\])""")
        return bracketRegex.findAll(this).map { it.value }.toList()
    }

    private fun String.hasAbba(): Boolean {
        return this.windowed(4).any { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }
    }

    private fun String.aba(): List<Pair<Char, Char>> {
        return this.windowed(3).filter { it[0] == it[2] && it[0] != it[1] }.map { it[0] to it[1] }
    }

    private fun String.supernetSubstrings(): List<String> {
        val hypernetSubstrings = hypernetSubstrings().map { "[$it]" }.toTypedArray()
        return this.split(*hypernetSubstrings)
    }

    private fun String.supportsSsl(): Boolean {
        val hypernetAba = hypernetSubstrings().map { it.aba() }.reduce { acc, list -> acc + list }
        val superNetAba = supernetSubstrings().map { it.aba() }.reduce { acc, list -> acc + list }
        return hypernetAba.any { it.second to it.first in superNetAba }


    }

    override fun part1(): Any {
        return input.asList.filterNotEmpty().count {
            it.supportsTls()
        }
    }

    override fun part2(): Any {
        return input.asList.filterNotEmpty().count {
            it.supportsSsl()
        }
    }
}
