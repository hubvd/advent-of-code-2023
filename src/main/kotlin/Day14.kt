package be.vandewalleh

fun main() {
    val grid = Grid.from(readText())
    val groups = grid.grouped()
    val rocks = groups['O']!!
    val squares = groups['#']!!

    fun move(rocks: List<Point>, translation: Point): List<Point> {
        val newRocks = rocks.toHashSet()

        val sorted = when (translation) {
            Point.up -> rocks.sortedBy { it.y }
            Point.down -> rocks.sortedByDescending { it.y }
            Point.left -> rocks.sortedBy { it.x }
            Point.right -> rocks.sortedByDescending { it.x }
            else -> throw IllegalStateException()
        }
        sorted.forEach { rock ->
            var prev: Point = rock

            while (true) {
                val newPoint = prev + translation
                if (newPoint !in grid || newPoint in squares || !newRocks.add(newPoint)) {
                    break
                }
                newRocks.remove(prev)
                prev = newPoint
            }
        }
        return newRocks.toList()
    }

    println(move(rocks, Point(0, -1)).sumOf { (grid.height - it.y) })

    fun cycle(rocks: List<Point>): List<Point> {
        var current = rocks
        current = move(current, Point.up)
        current = move(current, Point.left)
        current = move(current, Point.down)
        return move(current, Point.right)
    }

    fun detectCycle(rocks: List<Point>): Pair<Int, Int> {
        var current = rocks
        var currentCycle = 0
        val codesByCycles = HashMap<Int, Int>()
        while (true) {
            current = cycle(current)
            val hashcode = current.hashCode()
            val previousMatchingCycle = codesByCycles.putIfAbsent(hashcode, currentCycle)
            if (previousMatchingCycle != null) {
                return previousMatchingCycle to (currentCycle - previousMatchingCycle)
            }
            currentCycle++
        }
    }

    val (cycleStart, cycleLength) = detectCycle(rocks)
    val total = 1000000000
    val steps = total - (total - cycleStart) / cycleLength * cycleLength

    generateSequence(rocks) { cycle(it) }
        .drop(steps)
        .first()
        .sumOf { (grid.height - it.y) }
        .also { println(it) }
}
