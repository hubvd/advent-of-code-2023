package be.vandewalleh

import kotlin.math.max

fun main() {
    val lines = readLines()
    val re = Regex("(?<count>\\d+) (?<color>red|green|blue)")
    val games = lines.map {
        re.findAll(it).map { it.groups["count"]!!.value.toInt() to it.groups["color"]!!.value }.toList()
    }

    var count = 0
    games.forEachIndexed { index, game ->
        val ok = game.none { (count, color) ->
            when (color) {
                "red" -> count > 12
                "green" -> count > 13
                "blue" -> count > 14
                else -> error("")
            }
        }
        if (ok) count += index + 1
    }
    println(count)

    games.sumOf { game ->
        val counts = mutableMapOf<String, Int>()
        game.forEach { (count, color) ->
            counts.compute(color) { _, old -> max(count, old ?: 0) }
        }
        counts.values.reduce { acc, i -> acc * i }
    }.let { println(it) }
}
