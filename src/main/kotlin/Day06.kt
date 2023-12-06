package be.vandewalleh

fun main() {
    val input = readLines()
    fun compute(time: List<Long>, distance: List<Long>) = println(
        time.zip(distance).map { (time, goal) ->
            (1 until time).count { ((time - it) * it) > goal }
        }.reduce { acc, i -> acc * i },
    )
    input.map { it.parseLongs() }.let { (time, distance) -> compute(time, distance) }
    input.map { it.replace(" ", "").parseLongs() }.let { (time, distance) -> compute(time, distance) }
}
