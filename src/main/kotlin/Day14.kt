package be.vandewalleh

fun main() {
    val input = readLines()

    val points = input
        .asSequence()
        .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c != '.') (x at y) to c else null } }
        .flatten()
        .groupBy { it.second }

    val height = input.size
    val width = input.first().length

    val rocks = points['O']!!.map { it.first }
    val squares = points['#']!!.map { it.first }

    fun move(rocks: List<Point>, translation: Point): List<Point> {
        val newRocks = rocks.toHashSet()

        val sorted = when (translation) {
            Point(0, -1) -> rocks.sortedBy { it.y }
            Point(0, 1) -> rocks.sortedByDescending { it.y }
            Point(-1, 0) -> rocks.sortedBy { it.x }
            Point(1, 0) -> rocks.sortedByDescending { it.x }
            else -> throw IllegalStateException()
        }
        sorted.forEach { rock ->
            var prev: Point = rock

            while (true) {
                val newPoint = prev + translation
                if (newPoint.x !in 0 until width || newPoint.y !in 0 until height) {
                    break
                }
                if (newPoint in squares) {
                    break
                }
                if (newRocks.add(newPoint)) {
                    newRocks.remove(prev)
                    prev = newPoint
                } else {
                    break
                }
            }
        }
        return newRocks.toList()
    }

    println(move(rocks.toList(), Point(0, -1)).sumOf { (height - it.y).toLong() })

    fun cycle(rocks: List<Point>): List<Point> {
        var current = rocks.toList()
        current = move(current, Point(0, -1))
        current = move(current, Point(-1, 0))
        current = move(current, Point(0, 1))
        return move(current, Point(1, 0))
    }

    fun detectCycle(rocks: List<Point>): Pair<Int, Int> {
        var current = rocks.toList()
        var currentCycle = 0
        val codesByCycles = HashMap<Int, Int>()
        while (true) {
            current = cycle(current.toList())
            val hashcode = current.hashCode()
            val previousMatchingCycle = codesByCycles.putIfAbsent(hashcode, currentCycle)
            if (previousMatchingCycle != null) {
                return previousMatchingCycle to (currentCycle - previousMatchingCycle)
            }
            currentCycle++
        }
    }

    val (cycleStart, cycleLength) = detectCycle(rocks.toList())
    val total = 1000000000
    val n = total - cycleStart
    val m = n / cycleLength
    val x = total - (m * cycleLength)

    var current = rocks.toList()
    repeat(x) {
        current = cycle(current.toList())
    }
    println(current.sumOf { (height - it.y).toLong() })
}
