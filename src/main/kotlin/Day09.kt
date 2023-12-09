package be.vandewalleh

fun main() {
    val input = readLines().map { it.parseLongs() }

    fun find(line: List<Long>, position: List<*>.() -> Int, operator: (Long, Long) -> Long): Long {
        val lines = mutableListOf(ArrayDeque(line))

        while (!lines.last().all { it == 0L }) {
            lines += ArrayDeque(lines.last().zipWithNext().map { it.second - it.first })
        }

        lines.last().run { add(position(), 0L) }

        for (i in lines.size - 2 downTo 0) {
            with(lines[i]) {
                add(
                    position(),
                    operator(
                        this[position()],
                        lines[i + 1].run { this[position()] },
                    ),
                )
            }
        }

        return lines.first().run { this[position()] }
    }

    println(input.sumOf { find(it, { lastIndex }, { a, b -> a + b }) })
    println(input.sumOf { find(it, { 0 }, { a, b -> a - b }) })
}
