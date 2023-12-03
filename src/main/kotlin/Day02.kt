package be.vandewalleh

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/Day02.txt")!!.bufferedReader().readLines()
    val re = Regex("(?<count>\\d+) (?<color>red|green|blue)")
    val games = lines.map {
        it.substringAfter(": ").split(';').map {
            re.findAll(it).map { it.groups["count"]!!.value.toInt() to it.groups["color"]!!.value }.toList()
        }
    }
    var count = 0
    games.forEachIndexed { index, game ->
        val ok = game.all { subset ->
            subset.none { (count, color) ->
                when (color) {
                    "red" -> count > 12
                    "green" -> count > 13
                    "blue" -> count > 14
                    else -> error("")
                }
            }
        }
        if (ok) count += index + 1
    }
    println(count)

    games.sumOf { game ->
        val counts = mutableMapOf(
            "green" to 0,
            "red" to 0,
            "blue" to 0,
        )
        game.forEach { set ->
            set.forEach { (count, color) ->
                if (counts[color]!! < count) {
                    counts[color] = count
                }
            }
        }
        counts.values.reduce { acc, i -> acc * i }
    }.let { println(it) }

}
