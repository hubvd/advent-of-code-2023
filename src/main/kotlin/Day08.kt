package be.vandewalleh

import kotlin.math.abs

fun main() {
    val input = readLines()
    val directions = sequence {
        while (true) {
            input.first().forEach { yield(it) }
        }
    }

    val paths = readText().parseAll("(?<start>\\w+) = \\((?<left>.*), (?<right>.*)\\)") {
        this["start"]!!.value to (this["left"]!!.value to this["right"]!!.value)
    }.toMap()

    fun count(start: (String) -> Boolean, end: (String) -> Boolean) = paths.keys.filter(start).map {
        var count = 0
        var current = it

        for (direction in directions) {
            if (end(current)) break
            current = when (direction) {
                'L' -> paths[current]!!.first
                'R' -> paths[current]!!.second
                else -> throw IllegalStateException()
            }
            count++
        }
        count
    }

    fun gcd(a: Long, b: Long): Long = if (b == 0L) abs(a) else gcd(b, a % b)

    fun lcm(a: Long, b: Long) = abs(a * b) / gcd(a, b)

    println(count({ it == "AAA" }, { it == "ZZZ" }).first())
    println(count({ it.endsWith('A') }, { it.endsWith('Z') }).map { it.toLong() }.reduce { a, b -> lcm(a, b) })
}
