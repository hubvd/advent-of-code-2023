package be.vandewalleh

import kotlin.math.abs

fun main() {
    val galaxies = readLines()
        .mapIndexed { y, s -> s.mapIndexed { x, c -> (x at y) to c } }
        .flatten()
        .filter { it.second == '#' }
        .map { it.first }

    val maxX = galaxies.maxBy { it.x }.x
    val maxY = galaxies.maxBy { it.y }.y

    val xs = galaxies.map { it.x }.toSet()
    val ys = galaxies.map { it.y }.toSet()

    val emptyLines = (0..maxY).filter { it !in ys }
    val emptyColumns = (0..maxX).filter { it !in xs }

    fun compute(size: Int): Long {
        val expandedGalaxies = buildList {
            galaxies.forEach { (x, y) ->
                add(
                    Point(
                        x + (emptyColumns.count { it < x } * (size - 1)),
                        y + (emptyLines.count { it < y } * (size - 1)),
                    ),
                )
            }
        }

        return buildList {
            expandedGalaxies.indices.forEach { i ->
                for (j in i + 1 until expandedGalaxies.size) {
                    this.add(
                        expandedGalaxies[i] to expandedGalaxies[j],
                    )
                }
            }
        }
            .map { (a, b) -> abs(a.x - b.x) + abs(a.y - b.y) }
            .sumOf { it.toLong() }
    }
    println(compute(1000000))
    println(compute(2))
}
