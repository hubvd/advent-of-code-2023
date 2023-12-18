package be.vandewalleh

class Grid<T>(val values: Map<Point, T>, val width: Int, val height: Int) {
    val rowIndices = 0 until width
    val columnIndices = 0 until height

    operator fun contains(point: Point) = point.y in rowIndices && point.x in columnIndices

    operator fun get(point: Point) = values[point]

    fun grouped(): Map<T, List<Point>> = values.entries.groupBy({ it.value }, { it.key })

    companion object {
        fun from(text: String): Grid<Char> {
            val lines = text.lines()
            val height = lines.size
            val width = lines.first().length

            val values = lines
                .mapIndexed { y, s -> s.mapIndexed { x, c -> (x at y) to c } }
                .flatten()
                .toMap()

            return Grid(values, width, height)
        }
    }
}
