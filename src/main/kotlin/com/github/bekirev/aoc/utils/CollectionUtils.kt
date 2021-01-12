package com.github.bekirev.aoc.utils

fun <T> Collection<T>.possibleCombinations(): Sequence<List<T>> {
    fun possibleSequences(start: List<T>, left: Collection<T>): Sequence<List<T>> = when {
        left.isEmpty() -> sequenceOf(start)
        else -> left.asSequence().flatMap { next ->
            possibleSequences(start + next, left - next)
        }
    }
    return possibleSequences(emptyList(), this)
}