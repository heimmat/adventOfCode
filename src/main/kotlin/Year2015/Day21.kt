package Year2015

import Day

class Day21 : Day(2015,21) {
    private val weaponList: List<Equipment> = """
        Dagger        8     4       0
        Shortsword   10     5       0
        Warhammer    25     6       0
        Longsword    40     7       0
        Greataxe     74     8       0
    """.toEquipmentList()

    private val armorList = """
        Leather      13     0       1
        Chainmail    31     0       2
        Splintmail   53     0       3
        Bandedmail   75     0       4
        Platemail   102     0       5
    """.toEquipmentList().toMutableList().also { it.add(Equipment("Optional", 0,0,0)) }.toList()

    private val ringList = """
        Damage+1    25     1       0
        Damage+2    50     2       0
        Damage+3   100     3       0
        Defense+1   20     0       1
        Defense+2   40     0       2
        Defense+3   80     0       3
    """.toEquipmentList().toMutableList().also {
        it.add(Equipment("Optional", 0,0,0))
        it.add(Equipment("Optional 2", 0,0,0))
    }.toList()

    private fun String.toEquipmentList(): List<Equipment> = trimIndent().split("\n").filter { it != "" }.map {
        val (name,cost,damage,armor) = it.replace(Regex("\\s+"), " ").split(" ")
        Equipment(name,damage.toInt(),armor.toInt(),cost.toInt())
    }

    private fun equipmentCombinations(): List<List<Equipment>> {
        val listOfCombinations = mutableListOf<List<Equipment>>()
        for (weapon in weaponList) {
            for (armor in armorList) {
                for (ring1 in ringList) {
                    for (ring2 in ringList.toMutableList().also { it.remove(ring1) }.toList()) {
                        listOfCombinations.add(listOf(weapon,armor,ring1,ring2))
                    }
                }

            }
        }
        return listOfCombinations
    }

    /**
     * @return Player won the game
     */
    fun compete(player: Fighter, enemy: Fighter): Boolean {
        var attacker = player
        var defender = enemy
        while (player.isAlive && enemy.isAlive) {
            defender attackedBy attacker
            val tmp = attacker
            attacker = defender
            defender = tmp
        }
        return player.isAlive
    }

    override fun part1(): String {
        val (hp, damage, armor) = input.asList.filterNotEmpty().map { it.substringAfter(": ").toInt() }
        return equipmentCombinations().filter { combination ->
            compete(Fighter().also { it.equip(combination) }, Fighter(damage,armor,hp))
        }.minOf { it.sumBy { it.cost } }.toString()


    }

    override fun part2(): String {
        val (hp, damage, armor) = input.asList.filterNotEmpty().map { it.substringAfter(": ").toInt() }
        return equipmentCombinations().filter { combination ->
            !compete(Fighter().also { it.equip(combination) }, Fighter(damage,armor,hp))
        }.maxOf { it.sumBy { it.cost } }.toString()
    }

    data class Fighter(private val baseDamage: Int = 0, private val baseArmor: Int = 0, private var health: Int = 100) {
        private val equipment: MutableList<Equipment> = mutableListOf()

        val damage: Int get() = equipment.sumBy { it.damage } + baseDamage
        val armor: Int get() = equipment.sumBy { it.armor } + baseArmor
        val isAlive: Boolean get() = health > 0

        fun equip(newEquipment: List<Equipment>) = equipment.also { it.clear() }.addAll(newEquipment)

        infix fun attackedBy(attacker: Fighter) {
            val damageDealt = maxOf(attacker.damage - armor, 1)
            health-=damageDealt
        }

    }

    data class Equipment(val name: String, val damage: Int, val armor: Int, val cost: Int)
}
