package be.vandewalleh

fun main() {
    fun String.hash(): Int {
        var hash = 0
        forEach {
            hash += it.code
            hash *= 17
            hash %= 256
        }
        return hash
    }

    readLines().first().split(',').sumOf { it.hash() }.also { println(it) }

    val boxes = Array(256) { LinkedHashMap<String, Int>() }

    readLines().first().split(',').forEach {
        val box = boxes[it.split('-', '=')[0].hash()]
        if ('-' in it) {
            val label = it.substringBefore('-')
            box.remove(label)
        } else {
            val (label, lens) = it.split('=')
            box[label] = lens.toInt()
        }
    }

    var res = 0L
    boxes.forEachIndexed { index, box ->
        var slot = 1
        box.values.forEach { focalLength ->
            val power = (index + 1) * slot * focalLength
            res += power
            slot++
        }
    }
    println(res)
}
