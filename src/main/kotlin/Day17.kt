package be.vandewalleh

import kotlin.math.absoluteValue

private typealias Node = Pair<Point, Point>

private fun calculateHeatLoss(grid: Grid<Int>, straightLineRange: ClosedRange<Int>) {
    val nodesByCost = HashMap<Int, MutableList<Node>>()
    val costByNode = HashMap<Node, Int>()

    val start = 0 at 0
    val destination = grid.width - 1 at grid.height - 1

    fun addNode(point: Point, move: Point, cost: Int = 0) {
        val newPoint = point + move.coerceIn(-1..1)
        val newCost = grid[newPoint]?.let { it + cost } ?: return

        val node = newPoint to move
        if (costByNode.put(node, newCost) == null) {
            nodesByCost.computeIfAbsent(newCost, ::ArrayList) += node
        }
    }

    addNode(start, Point.right)
    addNode(start, Point.down)

    while (true) {
        val currentCost = nodesByCost.keys.min()
        val queue = nodesByCost.remove(currentCost)!!

        for ((point, move) in queue) {
            val length = if (move.y == 0) move.x.absoluteValue else move.y.absoluteValue
            if (point == destination && length >= straightLineRange.start) {
                println(currentCost)
                return
            }

            if (length >= straightLineRange.start) {
                addNode(
                    point = point,
                    move = Point(x = move.y, y = -move.x).coerceIn(-1..1),
                    cost = currentCost,
                )
                addNode(
                    point = point,
                    move = Point(x = -move.y, y = move.x).coerceIn(-1..1),
                    cost = currentCost,
                )
            }

            val max = straightLineRange.endInclusive
            val min = -straightLineRange.endInclusive + 1

            when {
                move.x in 1 until max -> addNode(
                    point,
                    move.copy(x = move.x + 1),
                    currentCost,
                )

                move.y in 1 until max -> addNode(
                    point,
                    move.copy(y = move.y + 1),
                    currentCost,
                )

                move.x in min until 0 -> addNode(
                    point,
                    move.copy(x = move.x - 1),
                    currentCost,
                )

                move.y in min until 0 -> addNode(
                    point,
                    move.copy(y = move.y - 1),
                    currentCost,
                )
            }
        }
    }
}

fun main() {
    val grid = Grid.from(readText()) { it.digitToInt() }
    calculateHeatLoss(grid, 0..3)
    calculateHeatLoss(grid, 4..10)
}
