package be.vandewalleh

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/Day01.txt")!!.bufferedReader().readLines()
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).map { it.toString() }
    val numbersStr = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    println(lines.sumOf { line ->
        "${findFirst(line, numbers)}${findLast(line, numbers)}".toInt()
    })
    println(lines.sumOf { line ->
        "${findFirst(line, numbers, numbersStr)}${findLast(line, numbers, numbersStr)}".toInt()
    })
}

private fun findFirst(line: String, vararg numbers: List<String>): Int {
    val value = numbers.mapNotNull { line.findAnyOf(it) }.minByOrNull { it.first }!!.second
    return numbers.filter { value in it }.map { it.indexOf(value) }.first() + 1
}

private fun findLast(line: String, vararg numbers: List<String>): Int {
    val value = numbers.mapNotNull { line.findLastAnyOf(it) }.maxByOrNull { it.first }!!.second
    return numbers.filter { value in it }.map { it.indexOf(value) }.first() + 1
}
