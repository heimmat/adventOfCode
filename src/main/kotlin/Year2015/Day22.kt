package Year2015
import Day
import kotlin.math.max

class Day22: Day(2015,22) {

    val bossHealth = input.asList.filterNotEmpty().first().substringAfter(": ").toInt()
    val bossDamage = input.asList.filterNotEmpty().last().substringAfter(": ").toInt()



    override fun part1(): String {
        val fights = (1..10000).map {
            combat()
        }
        return fights.filter { it.first }.minOf { it.second }.toString()
    }

    override fun part2(): String {
        val fights = (1..100000).map {
            combat(true)
        }
        return fights.filter { it.first }.minOf { it.second }.toString()
    }

    fun combat(part2: Boolean = false, debug: Boolean = false): Pair<Boolean, Int> {
        var playerHealth = 50
        var bossHealth = bossHealth
        var round = 1
        var manaLeft = 500
        var manaSpent = 0
        val spellsInEffect: MutableList<Pair<Spell, Int>> = mutableListOf()
        var playerWon: Boolean? = null

        fun playerArmor(): Int {
            return if (spellsInEffect.any { it.first == Spell.Shield }) 7 else 0
        }

        while (playerHealth > 0 && bossHealth > 0) {
            if (debug) println("\nRound $round")
            //Apply effects
            spellsInEffect.forEach {
                when (it.first) {
                    Spell.Poison -> {
                        bossHealth -= 3
                        if (debug) println("Poison dealt 3 damage -> bossHealth: $bossHealth")
                    }
                    Spell.Recharge -> {
                        manaLeft += 101
                        if (debug) println("Recharged 101 mana -> manaLeft: $manaLeft")
                    }

                    else -> {}
                }

            }
            //Remove spells that age out
            spellsInEffect.removeIf {
                if (debug) println("Active spell ${it.first.name}")
                if (debug) println("\tRound $round - duration ${it.first.duration}")
                if (debug) println("\tAdded in round ${it.second}")
                round - it.first.duration!! >= it.second
            }
            if (round % 2 == 1) {
                //Player attacks
                if (part2) {
                    playerHealth--
                    if (playerHealth <= 0) {
                        playerWon = false
                        break
                    }
                }
                if (debug) println("Player's turn")
                val possibleSpells = Spell.values().filter { it.cost < manaLeft && it !in spellsInEffect.map { it.first } }
                if (debug) println("There are ${possibleSpells.count()} spells to choose")
                if (possibleSpells.isEmpty()) {
                    playerWon = false
                    break
                }
                val chosenSpell = possibleSpells.random()
                when (chosenSpell) {
                    Spell.MagicMissile -> {
                        bossHealth -= 4
                        if (debug) println("Magic Missile dealt 4 damage -> bossHealth: $bossHealth")
                    }
                    Spell.Drain -> {
                        bossHealth -= 2
                        playerHealth += 2
                        if (debug) println("Drained 2 health to player -> bossHealth: $bossHealth, playerHealth: $playerHealth")
                    }
                    else -> {
                        spellsInEffect.add(chosenSpell to round)
                        if (debug) println("Added spell ${chosenSpell.name} in round $round")
                    }
                }
                manaSpent += chosenSpell.cost
                manaLeft -= chosenSpell.cost
                if (debug) println("Mana spent: $manaSpent, manaLeft: $manaLeft")
            } else {
                //Boss attacks
                if (debug) println("Boss's turn")
                if (bossHealth > 0) {
                    val damageDealt = max(bossDamage - playerArmor(),1)
                    playerHealth -= damageDealt
                    if (debug) println("Boss dealt $damageDealt damage -> playerHealth: $playerHealth")
                } else {
                    if (debug) println("Boss is dead")
                }

            }

            round++
        }

        if (playerWon == null) {
            playerWon = playerHealth > 0
        }
        return playerWon to manaSpent
    }


    enum class Spell(val cost: Int, val duration: Int? = null) {
        MagicMissile(53),
        Drain(73),
        Shield(113,6),
        Poison(173,6),
        Recharge(229,5)
    }


}