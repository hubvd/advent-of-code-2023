package be.vandewalleh

data class Ranges(val destination: LongRange, val source: LongRange)

fun main() {
    val input = readText().split("\n\n")
    val seeds = Regex("\\d+").findAll(input[0]).map { it.value.toLong() }.toList()
    val maps = input.drop(1).map {
        it.lines().drop(1).map {
            val (dStart, sStart, length) = it.split(" ").map { it.toLong() }
            Ranges(
                dStart until dStart + length,
                sStart until sStart + length,
            )
        }
    }
    val locations = seeds.map { seed ->
        var value = seed
        maps.forEach { ranges ->
            ranges.find { value in it.source }?.let { (destination, source) ->
                value = value - source.first + destination.first
            }
        }
        value
    }
    println(locations.min())

    val seedRanges = seeds.chunked(2)
    println(seedRanges.parallelStream().mapToLong {
        var min = Long.MAX_VALUE
        for (seed in it.first() until it.first() + it.last()) {
            var value = seed
            maps.forEach { ranges ->
                ranges.find { value in it.source }?.let { (destination, source) ->
                    value = value - source.first + destination.first
                }
            }
            if (value < min) min = value
        }
        min
    }.min())
}
