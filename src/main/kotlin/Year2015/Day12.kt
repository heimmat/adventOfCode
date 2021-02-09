package Year2015

import Day
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

class Day12 : Day(2015,12) {
    override fun part1(): String {
        return input.asString.map { if (it.isDigit() || it == '-') it else ',' }.joinToString("").split(",").filter { it != "" }.map { it.toInt() }.sum().toString()
    }

    override fun part2(): String {
        val decoded = Json.decodeFromString<JsonElement>(input.asString)
        return decoded.sum().toString()
    }

    private fun JsonElement.sum(): Int {
        if (this is JsonObject) {
            val obj = this
            if (obj.values.contains(JsonPrimitive("red"))) return 0
            return obj.entries.sumBy { it.value.sum() }
        } else  if (this is JsonArray) {
            val arr = this
            return arr.sumBy { it.sum() }
        } else if (this is JsonPrimitive) {
            val prim = this
            if (!prim.isString) {
                return prim.int
            } else {
                return 0
            }
        } else {
            return 0
        }
    }



}
