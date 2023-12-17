package be.vandewalleh

import kotlin.math.roundToInt

fun main() {
    val groups = readText().split("\n\n")

    fun findHorizontal(group: List<String>): Int? {
        outer@ for (middle in 1 until group.size) {
            for (before in 0 until middle) {
                val readMiddle = middle - 0.5
                val diff = readMiddle - before
                val nextIndex = (readMiddle + diff).roundToInt()
                val current = group[before]
                val next = group.getOrElse(nextIndex) { current }
                if (next != current) {
                    continue@outer
                }
            }
            return middle
        }
        return null
    }

    fun findHorizontal2(group: List<String>): Int? {
        outer@ for (middle in 1 until group.size) {
            var differences = 0
            for (before in 0 until middle) {
                val readMiddle = middle - 0.5
                val diff = readMiddle - before
                val nextIndex = (readMiddle + diff).roundToInt()
                val current = group[before]
                val next = group.getOrElse(nextIndex) { current }
                differences += current.indices.count { i -> current[i] != next[i] }
                if (differences > 1) {
                    continue@outer
                }
            }
            if (differences != 1) {
                continue@outer
            }
            return middle
        }
        return null
    }

    groups.sumOf {
        val lines = it.lines()
        var res = findHorizontal2(lines)?.let { it * 100 }
        if (res == null) {
            val cols = lines.first().indices.map { i ->
                lines.map { it[i] }.joinToString("")
            }
            res = findHorizontal2(cols)
        }

        res?.toLong() ?: error(it)
    }.let { println(it) }
}
