package be.vandewalleh

data class Point(val x: Int, val y: Int)

infix fun Int.at(y: Int) = Point(this, y)

operator fun Point.plus(other: Point) = Point(this.x + other.x, this.y + other.y)
