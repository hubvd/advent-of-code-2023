package be.vandewalleh

import kotlin.math.absoluteValue

fun main() {
    var input = readLines()

    val height = input.size
    val width = input.first().length

    val points = input
        .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c != '.') (x at y) to c else null } }
        .flatten()
        .toMap()

    println(points)

    fun next(point: Point, direction: Point): List<Point> {
        val char = points[point] ?: return listOf(direction)
        return when (char) {
            '/' -> {
                when (direction) {
                    Point(1, 0) -> listOf(Point(0, -1))

                    Point(0, 1) -> listOf(Point(-1, 0))

                    Point(0, -1) -> listOf(Point(1, 0))

                    Point(-1, 0) -> listOf(Point(0, 1))

                    else -> TODO(direction.toString())
                }
            }

            '\\' -> when (direction) {
                Point(1, 0) -> listOf(Point(0, 1))

                Point(0, -1) -> listOf(Point(-1, 0))

                Point(-1, 0) -> listOf(Point(0, -1))

                Point(0, 1) -> listOf(Point(1, 0))

                else -> TODO(direction.toString())
            }

            '-' -> {
                if (direction.x.absoluteValue == 1) {
                    listOf(direction)
                } else {
                    listOf(Point(-1, 0), Point(1, 0))
                }
            }

            '|' -> {
                if (direction.y.absoluteValue == 1) {
                    listOf(direction)
                } else {
                    listOf(Point(0, -1), Point(0, 1))
                }
            }

            else -> TODO()
        }
    }

    fun solve(startingPoint: Point, initialDirection: Point): Int {
        val states = HashSet<Pair<Point, Point>>()
        val queue = ArrayList<Pair<Point, Point>>()
        val visited = HashSet<Point>()
        states += startingPoint to initialDirection
        queue += states.first()
        visited += startingPoint
        while (queue.isNotEmpty()) {
            val (point, direction) = queue.removeLast()
            val next = next(point, direction)
            for (newDirection in next) {
                val newPoint = newDirection + point
                if (newPoint.x !in 0 until width || newPoint.y !in 0 until height) {
                    continue
                }
                visited += newPoint
                if (!states.add(newPoint to newDirection)) {
                    continue
                }

                queue.add(newPoint to newDirection)
            }
        }
        return visited.size
    }

    println(solve(Point(0, 0), Point(1, 0)))

    sequence {
        for (x in 0 until width) {
            yield(Point(x, 0) to Point(0, 1))
            yield(Point(x, height - 1) to Point(0, -1))
        }

        for (y in 0 until height) {
            yield(Point(0, y) to Point(1, 0))
            yield(Point(width - 1, y) to Point(-1, 0))
        }
    }.map { solve(it.first, it.second) }.max().also { println(it) }
}
