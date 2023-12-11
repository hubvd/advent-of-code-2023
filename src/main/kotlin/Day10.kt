package be.vandewalleh

fun main() {
    fun connectedPoints(char: Char): List<Point> = when (char) {
        '|' -> listOf(0 at -1, 0 at 1)
        '-' -> listOf(-1 at 0, 1 at 0)
        'L' -> listOf(1 at 0, 0 at -1) // top right
        'J' -> listOf(-1 at 0, 0 at -1) // top left
        '7' -> listOf(-1 at 0, 0 at 1) // bottom left
        'F' -> listOf(1 at 0, 0 at 1) // bottom right
        '.' -> emptyList()
        else -> throw IllegalStateException(char.toString())
    }

    val map = readText()
        .lines()
        .mapIndexed { y, s -> s.mapIndexed { x, c -> (x at y) to c } }
        .flatten().toMap().toMutableMap()

    val start = map.entries.find { it.value == 'S' }!!.key

    val startingPoint = sequence {
        yield(0 at 1)
        yield(1 at 0)
        yield(0 at -1)
        yield(-1 at 0)
    }.map { start + it }
        .filter { it in map }
        .first { point -> connectedPoints(map[point]!!).map { it + point }.contains(start) }

    val visited = HashSet<Point>()
    visited.add(start)
    visited.add(startingPoint)
    var current = startingPoint
    while (true) {
        val char = map[current]!!
        val next = connectedPoints(char).map { current + it }.find { it != start && it !in visited }
        if (next == null) {
            break
        }
        visited += next
        current = next
    }
    println((visited.size) / 2)

    val maxX = map.keys.maxBy { it.x }.x
    val maxY = map.keys.maxBy { it.y }.y

    map[start] = '7' // TODO

    var count = 0
    for (x in 0..maxX) {
        var outside = true
        for (y in 0..maxY) {
            val point = x at y
            val char = map[point]!!
            if (point in visited) {
                if (connectedPoints(char).any { it.x == 1 }) {
                    outside = !outside
                }
            } else if (!outside) {
                count++
            }
        }
    }
    println(count)
}
