package be.vandewalleh

import kotlin.math.pow

fun main() {
    val lines = readLines()
    val lineRe = Regex("Card.*:(.*)\\|(.*)$")
    val whitespaceRe = Regex("\\s+")
    val part2 = IntArray(lines.size) { 1 }
    lines.map { line ->
        val (winning, current) = lineRe.find(line)!!.groupValues.drop(1)
            .map { it.trim().split(whitespaceRe).map { it.toInt() }.toHashSet() }
        current.apply { retainAll(winning) }
    }.onEachIndexed { i, current ->
        (i until current.size + i).forEach {
            part2[it + 1] += part2[i]
        }
    }.sumOf {
        2f.pow(it.size.toFloat() - 1).toInt()
    }.let { println(it) }
    println(part2.sum())
}
