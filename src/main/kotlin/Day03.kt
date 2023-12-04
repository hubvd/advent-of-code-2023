package be.vandewalleh

fun main() {
    val lines = readLines()
    val numRe = Regex("\\d+")
    val numbers = lines.flatMapIndexed { index: Int, s: String ->
        numRe.findAll(s).map { (index to it.range) to it.value.toInt() }.toList()
    }
    numbers.filter { (coords) ->
        val (y, xRange) = coords
        var keep = false
        outer@ for (yy in y - 1..y + 1) {
            for (xx in xRange.first - 1..xRange.last + 1) {
                val symbol = lines.getOrNull(yy)?.getOrNull(xx) ?: continue
                if (!symbol.isDigit() && symbol != '.') {
                    keep = true
                    break@outer
                }
            }
        }
        keep
    }.sumOf { it.second.toLong() }
        .let { println(it) }

    lines
        .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == '*') y to x else null } }
        .flatten()
        .mapNotNull { (gearY, gearX) ->
            numbers.asSequence().filter { (coords) ->
                val (y, xRange) = coords
                var keep = false
                outer@ for (yy in y - 1..y + 1) {
                    for (xx in xRange.first - 1..xRange.last + 1) {
                        if (yy == gearY && xx == gearX) {
                            keep = true
                            break@outer
                        }
                    }
                }
                keep
            }
                .take(2)
                .toList()
                .takeIf { it.size == 2 }
                ?.let { it[0].second * it[1].second.toLong() }
        }
        .sum()
        .let { println(it) }
}
