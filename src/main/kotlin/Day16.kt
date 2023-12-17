package be.vandewalleh

import kotlin.math.absoluteValue

fun main() {
    val input = readLines()

    val height = input.size
    val width = input.first().length

    val points = input
        .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c != '.') (x at y) to c else null } }
        .flatten()
        .toMap()

    val right = Point(1, 0)
    val up = Point(0, -1)
    val down = Point(0, 1)
    val left = Point(-1, 0)

    fun next(point: Point, direction: Point): List<Point> {
        val char = points[point] ?: return listOf(direction)
        return when (char) {
            '/' -> {
                when (direction) {
                    right -> listOf(up)
                    down -> listOf(left)
                    up -> listOf(right)
                    left -> listOf(down)
                    else -> throw IllegalStateException()
                }
            }

            '\\' -> when (direction) {
                right -> listOf(down)
                up -> listOf(left)
                left -> listOf(up)
                down -> listOf(right)
                else -> throw IllegalStateException()
            }

            '-' -> {
                if (direction.x.absoluteValue == 1) {
                    listOf(direction)
                } else {
                    listOf(left, right)
                }
            }

            '|' -> {
                if (direction.y.absoluteValue == 1) {
                    listOf(direction)
                } else {
                    listOf(up, down)
                }
            }

            else -> throw IllegalStateException()
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

    println(solve(Point(0, 0), right))

    sequence {
        for (x in 0 until width) {
            yield(Point(x, 0) to down)
            yield(Point(x, height - 1) to up)
        }

        for (y in 0 until height) {
            yield(Point(0, y) to right)
            yield(Point(width - 1, y) to left)
        }
    }.maxOfOrNull { solve(it.first, it.second) }.also { println(it) }
}
