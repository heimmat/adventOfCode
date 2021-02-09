package Year2015

import Day

class Day19 : Day(2015,19) {
    private val molecule = input.asList.filterNotEmpty().last()
    private val replacements = input.asList.filterNotEmpty().dropLast(1).map {
        val (lh,rh) = it.split(" => ")
        lh to rh
    }

    override fun part1(): String {
        return possibleMoleculesAfterOneReplacement(molecule,replacements).size.toString()
    }

    override fun part2(): String {
        var localMolecule = molecule
        val localReplacements = replacements
        var count = 0
        while(localMolecule != "e") {
            val randomIndex = localReplacements.indices.random()
            val randomReplacement = localReplacements.elementAt(randomIndex)
            //println("randomReplacement: $randomReplacement, replLength: ${localReplacements.size}, localMoleculeLength: ${localMolecule.length}")
            //println(localMolecule)
            while (localMolecule.contains(randomReplacement.second)) {
                val indexOfLast = localMolecule.lastIndexOf(randomReplacement.second)
                //println("count $count, indexOfLast: $indexOfLast, ende: ${indexOfLast+randomReplacement.second.length}")
                localMolecule = localMolecule.replaceRange(indexOfLast until indexOfLast+randomReplacement.second.length, randomReplacement.first)
                count++
            }
        }
        return count.toString()
    }

    private fun possibleMoleculesAfterOneReplacement(molecule: String, replacements: List<Pair<String,String>>): Set<String> {
        val set: MutableSet<String> = mutableSetOf()
        replacements.forEach {
            set.addAll(possibleMoleculesAfterOneReplacement(molecule, it))
        }
        return set
    }

    private fun possibleMoleculesAfterOneReplacement(molecule: String, replacement: Pair<String,String>): Set<String> {
        val set: MutableSet<String> = mutableSetOf()
        val regex = Regex(replacement.first)
        val occurrences = regex.findAll(molecule)
        occurrences.forEach {
            set.add(molecule.replaceRange(it.range, replacement.second))
        }
        return set
    }
}
