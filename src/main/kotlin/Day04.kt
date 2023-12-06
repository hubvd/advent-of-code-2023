package be.vandewalleh

import kotlin.math.pow

fun main() {
    val lines = readLines()
    val part2 = IntArray(lines.size) { 1 }
    lines.map { line ->
        val (winning, current) = line
            .split(':', '|')
            .drop(1)
            .map { it.parseInts().toHashSet() }
        current.intersect(winning).size
    }.onEachIndexed { i, current ->
        (i until current + i).forEach {
            part2[it + 1] += part2[i]
        }
    }.sumOf {
        2f.pow(it.toFloat() - 1).toInt()
    }.let { println(it) }
    println(part2.sum())
}
