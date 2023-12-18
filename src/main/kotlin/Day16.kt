package be.vandewalleh

import kotlin.math.absoluteValue

fun main() {
    val grid = Grid.from(readText())

    fun next(point: Point, direction: Point): List<Point> {
        val char = grid[point]?.takeIf { it != '.' } ?: return listOf(direction)
        return when (char) {
            '/' -> {
                when (direction) {
                    Point.right -> listOf(Point.up)
                    Point.down -> listOf(Point.left)
                    Point.up -> listOf(Point.right)
                    Point.left -> listOf(Point.down)
                    else -> throw IllegalStateException()
                }
            }

            '\\' -> when (direction) {
                Point.right -> listOf(Point.down)
                Point.up -> listOf(Point.left)
                Point.left -> listOf(Point.up)
                Point.down -> listOf(Point.right)
                else -> throw IllegalStateException()
            }

            '-' -> {
                if (direction.x.absoluteValue == 1) {
                    listOf(direction)
                } else {
                    listOf(Point.left, Point.right)
                }
            }

            '|' -> {
                if (direction.y.absoluteValue == 1) {
                    listOf(direction)
                } else {
                    listOf(Point.up, Point.down)
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
                if (newPoint !in grid) {
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

    println(solve(Point(0, 0), Point.right))

    sequence {
        for (x in grid.columnIndices) {
            yield(Point(x, 0) to Point.down)
            yield(Point(x, grid.height - 1) to Point.up)
        }

        for (y in grid.rowIndices) {
            yield(Point(0, y) to Point.right)
            yield(Point(grid.width - 1, y) to Point.left)
        }
    }.maxOfOrNull { solve(it.first, it.second) }.also { println(it) }
}
