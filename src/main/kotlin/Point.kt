package be.vandewalleh

data class Point(val x: Int, val y: Int) {
    override fun toString() = "($x,$y)"

    companion object {
        val right = Point(1, 0)
        val up = Point(0, -1)
        val down = Point(0, 1)
        val left = Point(-1, 0)
    }
}

infix fun Int.at(y: Int) = Point(this, y)

operator fun Point.plus(other: Point) = Point(this.x + other.x, this.y + other.y)
operator fun Point.minus(other: Point) = Point(this.x - other.x, this.y - other.y)

fun Point.coerceIn(range: ClosedRange<Int>) = Point(x = x.coerceIn(range), y = y.coerceIn(range))
