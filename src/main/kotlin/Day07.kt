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

    fun compareOccurrences(o1: List<String>, o2: List<String>): Int {
        val groups1 = o1[0].groupBy { it }.mapValues { it.value.size }.values.sorted()
        val groups2 = o2[0].groupBy { it }.mapValues { it.value.size }.values.sorted()
        return groups.indexOf(groups2).compareTo(groups.indexOf(groups1))
    }

    fun compareCards(o1: List<String>, o2: List<String>): Int {
        for ((c1, c2) in o1[0].toCharArray().zip(o2[0].toCharArray())) {
            return when {
                c1 == c2 -> continue
                else -> cards.indexOf(c2).compareTo(cards.indexOf(c1))
            }
        }
        return 0
    }

    fun expand(card: String): List<String> {
        var values = listOf<String>()
        for (c in card) {
            values = when (c) {
                'J' -> buildList {
                    if (values.isEmpty()) {
                        for (char in cards) {
                            if (char == 'J') continue
                            add(char.toString())
                        }
                    } else {
                        values.forEach { current ->
                            for (char in cards) {
                                if (char == 'J') continue
                                add(current + char)
                            }
                        }
                    }
                }

                else -> {
                    if (values.isEmpty()) listOf(c.toString())
                    else values.map { it + c }
                }
            }
        }
        return values
    }

    fun best(card: String): String = expand(card)
        .map { listOf(it) }
        .sortedWith(::compareOccurrences)
        .last()
        .first()

    readLines()
        .map { it.split(' ') }
        .sortedWith(Comparator(::compareOccurrences).then(::compareCards))
        .mapIndexed { index, (_, score) -> (index + 1) * score.toInt() }
        .sum()
        .let { println(it) }

    readLines()
        .asSequence()
        .map { it.split(' ') }
        .sortedWith(Comparator<List<String>> { o1, o2 ->
            compareOccurrences(
                listOf(best(o1[0]), o1[1]),
                listOf(best(o2[0]), o2[1])
            )
        }.then { a, b ->
            compareCards(
                listOf(a[0].replace('J', '1'), a[1]),
                listOf(b[0].replace('J', '1'), b[1]),
            )
        })
        .mapIndexed { index, (_, score) -> (index + 1) * score.toInt() }
        .sum()
        .let { println(it) }
}
