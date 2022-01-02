package com.github.bekirev.aoc.year2021.day01

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2021.Year2021Day

fun main() = Day.run(Year2021Day01)

object Year2021Day01 : Year2021Day(1) {
    override fun first(input: String): Int =
        input.measurements()
            .zipWithNext()
            .speed()

    override fun second(input: String): Int =
        input.measurements()
            .zipWithNNext(3)
            .map { it.sum() }
            .zipWithNext()
            .speed()

    private fun String.measurements() =
        lineSequence()
            .mapNotNull(String::toIntOrNull)

    private fun Sequence<Pair<Int, Int>>.speed(): Int =
        this
            .map { (prev, cur) ->
                cur - prev
            }
            .count { diff -> diff > 0 }

    private fun <T> Sequence<T>.zipWithNNext(n: Int): Sequence<List<T>> = sequence {
        var tuple = mutableListOf<T>()
        for (elem in this@zipWithNNext) {
            tuple += elem
            if (tuple.size == n) {
                yield(tuple)
                tuple = tuple.subList(1, n).toMutableList()
            }
        }
    }
}
