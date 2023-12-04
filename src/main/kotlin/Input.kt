package be.vandewalleh

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
