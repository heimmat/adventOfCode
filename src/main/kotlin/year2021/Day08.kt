package year2021

import Day

class Day08(debug: Boolean = false): Day(2021,8, debug) {
    private val testInput = """
        be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
        edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
        fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
        fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
        aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
        fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
        dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
        bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
        egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
        gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
    """.trimIndent()

    private val signals = (if (debug) testInput.split("\n") else input.asList)
        .map {
            val (input, output) = it.split(" | ")
                .map {
                    it.split(" ")
                }
            input to output
        }

    override fun part1(): Any {
        return signals.sumOf {
            it.second.count {
                it.length in setOf(2,3,4,7)
            }
        }
    }

    override fun part2(): Any {
//        val mapping = signals[2].deriveMapping()
//        println("mapping: ${mapping}")
//        println("inverted: ${mapping.invert()}")
//        return mapping

        return signals.map { signal ->
            val mapping = signal.deriveMapping().map { it.value to it.key }.toMap()
            signal.second.map { it.decodeNumber(mapping) }.joinToString("").toInt()
        }.sum()
    }

    private fun Map<Char,Char>.invert(): Map<Char,Char> {
        return this.map { it.value to it.key }.toMap()
    }

    private fun String.decodeNumber(mapping: Map<Char,Char>): Int? {
        val map = mapOf(
            "abcefg" to 0,
            "cf" to 1,
            "acdeg" to 2,
            "acdfg" to 3,
            "bcdf" to 4,
            "abdfg" to 5,
            "abdefg" to 6,
            "acf" to 7,
            "abcdefg" to 8,
            "abcdfg" to 9
        )
        val translatedString = this.map { mapping[it] }.sortedBy { it }.joinToString("")
        return map[translatedString]
    }

    private fun Pair<List<String>, List<String>>.deriveMapping(): Map<Char,Char> {
        val mutableMap = mutableMapOf<Char,Char>()
        val inOut = this.first + this.second
        val one = inOut.firstOrNull { it.length == 2}
        val seven = inOut.firstOrNull { it.length == 3 }
        val four = inOut.firstOrNull { it.length == 4 }
        val eight = inOut.firstOrNull { it.length == 7}
        if (seven != null && one != null) {
            mutableMap.put('a', seven.first { it !in one })
            if (debug) println("Found one $one and seven $seven")
            if (debug) println(mutableMap)
        }
        if (four != null && seven != null) {
            if (debug) println("Found four $four and seven $seven")
            if (debug) println(mutableMap)

            mutableMap.put('a', seven.first { it !in four })
            val nine = inOut.firstOrNull { s ->
                s.length == 6 && (seven + four).all { it in s }
            }?.let { nine ->
                if (debug) println("Found nine $nine")
                mutableMap.put('g', (nine.first { it !in (seven + four) }))
                if (debug) println(mutableMap)

                if (eight != null) {
                    if (debug) println("Found eight $eight")
                    mutableMap.put('e', eight.first { it !in nine })
                    if (debug) println(mutableMap)

                    val aeg = "aeg".map { mutableMap.get(it)!! }
                    val zero = inOut.firstOrNull { cand ->
                        cand.length == 5 && aeg.all { it in cand } && seven.all { it in cand }
                    }?.also { zero ->
                        if (debug) println("found zero $zero")
                        mutableMap.put('b', zero.first { it !in aeg && it !in seven })
                        if (debug) println(mutableMap)
                    }

                    val two = inOut.firstOrNull { cand ->
                        cand.length == 5 && aeg.all { it in cand} && seven.any { it !in cand }
                    }?.also { two ->
                        if (debug) println("Found two $two")
                        mutableMap.put('c', two.first { it != mutableMap.get('a')!! && it in seven })
                        mutableMap.put('d', two.first { it !in mutableMap.values })

                        if (debug) println("Three cands = ${inOut.filter { cand -> cand.length == 5 && "acdg".map { mutableMap.get(it)!! }.all { it in cand } }}")
                        val three = inOut.firstOrNull { cand ->
                            cand.length == 5 && "acdg".map { mutableMap.get(it)!! }.all { it in cand } && cand != two
                        }?.also { three ->
                            if (debug) println("Found three $three")
                            mutableMap.put('f', three.first { it !in "acdg".map { mutableMap.get(it)!! } })
                            mutableMap.put('b', "abcdefg".first { it !in mutableMap.values })
                        }
                    }



                }
            }
        }
        return mutableMap
    }
}