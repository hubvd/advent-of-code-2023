package be.vandewalleh

import kotlin.streams.asStream

data class Ranges(val destination: LongRange, val source: LongRange)

fun main() {
    val input = readText().split("\n\n")
    val seeds = input[0].parseLongs()
    val maps = input.drop(1).map {
        it.lines().drop(1).map {
            val (dStart, sStart, length) = it.parseLongs()
            Ranges(
                dStart until dStart + length,
                sStart until sStart + length,
            )
        }
    }

    fun compute(seed: Long): Long {
        var value = seed
        maps.forEach { ranges ->
            ranges.find { value in it.source }?.let { (destination, source) ->
                value = value - source.first + destination.first
            }
        }
        return value
    }

    println(seeds.minOfOrNull { compute(it) })
    println(
        sequence {
            seeds.chunked(2).forEach {
                for (seed in it.first() until it.first() + it.last()) {
                    yield(seed)
                }
            }
        }.asStream().parallel().mapToLong { compute(it) }.min(),
    )
}
