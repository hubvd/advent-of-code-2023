package be.vandewalleh

fun main() {
    val cards = arrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', '1')
    val groups = listOf(
        listOf(5),
        listOf(1, 4),
        listOf(2, 3),
        listOf(1, 1, 3),
        listOf(1, 2, 2),
        listOf(1, 1, 1, 2),
        listOf(1, 1, 1, 1, 1),
    )

    fun compareOccurrences(o1: String, o2: String): Int {
        val groups1 = o1.groupBy { it }.mapValues { it.value.size }.values.sorted()
        val groups2 = o2.groupBy { it }.mapValues { it.value.size }.values.sorted()
        return groups.indexOf(groups2).compareTo(groups.indexOf(groups1))
    }

    fun compareCards(o1: String, o2: String): Int {
        for ((c1, c2) in o1.toCharArray().zip(o2.toCharArray())) {
            return when {
                c1 == c2 -> continue
                else -> cards.indexOf(c2).compareTo(cards.indexOf(c1))
            }
        }
        return 0
    }

    fun best(card: String): String {
        val mostCommon = card.groupBy { it }
            .mapValues { it.value.size }
            .entries
            .filter { it.key != 'J' }
            .maxByOrNull { it.value }
            ?.key
            ?: 'A'

        return card.replace('J', mostCommon)
    }

    readLines()
        .map { it.split(' ') }
        .sortedWith(
            compareBy<List<String>, String>(::compareOccurrences) { it.first() }
                .then(compareBy(::compareCards) { it.first() }),
        )
        .mapIndexed { index, (_, score) -> (index + 1) * score.toInt() }
        .sum()
        .let { println(it) }

    readLines()
        .asSequence()
        .map { it.split(' ') }
        .sortedWith(
            compareBy<List<String>, String>(::compareOccurrences) { best(it.first()) }
                .then(compareBy(::compareCards) { it.first().replace('J', '1') }),
        )
        .mapIndexed { index, (_, score) -> (index + 1) * score.toInt() }
        .sum()
        .let { println(it) }
}
