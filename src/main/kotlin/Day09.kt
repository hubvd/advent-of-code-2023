package be.vandewalleh

fun main() {
    val input = readLines().map { it.parseLongs() }

    fun find(
        line: List<Long>,
        adder: MutableList<Long>.(Long) -> Unit,
        getter: (List<Long>) -> Long,
        operator: (Long, Long) -> Long,
    ): Long {
        val lines = mutableListOf(ArrayDeque(line))

        while (!lines.last().all { it == 0L }) {
            lines += ArrayDeque(lines.last().zipWithNext().map { it.second - it.first })
        }

        with(lines.last()) {
            adder(0L)
        }

        for (i in lines.size - 2 downTo 0) {
            with(lines[i]) {
                adder(operator(lines[i].run(getter), lines[i + 1].run(getter)))
            }
        }

        return lines.first().run(getter)
    }

    println(input.sumOf { find(it, { addLast(it) }, { it.last() }, { a, b -> a + b }) })
    println(input.sumOf { find(it, { addFirst(it) }, { it.first() }, { a, b -> a - b }) })
}
