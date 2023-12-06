package be.vandewalleh

import org.intellij.lang.annotations.Language

fun readText(): String {
    val clazz = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass()
    return clazz.getResourceAsStream(
        "/" + clazz
            .name
            .removePrefix("be.vandewalleh.")
            .removeSuffix("Kt") + ".txt",
    )!!.bufferedReader().readText().trimEnd()
}

@Suppress("NOTHING_TO_INLINE")
inline fun readLines() = readText().lines()

fun <T> String.parseAll(@Language("RegExp") regex: String, mapper: MatchGroupCollection.(String) -> T): List<T> {
    return regex.toRegex().findAll(this).map { with(it.groups) { mapper(it.value) } }.toList()
}

fun String.parseLongs(): List<Long> = parseAll("\\d+") { it.toLong() }

fun String.parseInts(): List<Int> = parseAll("\\d+") { it.toInt() }
