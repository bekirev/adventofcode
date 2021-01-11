package com.github.bekirev.aoc.year2015.day10

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day10)

object Year2015Day10 : Year2015Day(10) {
    override fun first(input: String): Int =
        lookAndSay(input.trim(), 40).length
}

fun lookAndSay(sequence: String, iterationsCount: Int): String = when (iterationsCount) {
    0 -> sequence
    else -> lookAndSay(lookAndSay(sequence), iterationsCount - 1)
}

fun lookAndSay(sequence: String): String {
    fun CharSequence.toLookAndSaySequence(): Sequence<Pair<Char, Int>> = sequence {
        var prevChar: Char? = null
        var prevCharCount = 0
        for (char in this@toLookAndSaySequence) {
            when (prevChar) {
                null -> {
                    prevChar = char
                    prevCharCount = 1
                }
                char -> ++prevCharCount
                else -> {
                    yield(prevChar to prevCharCount)
                    prevChar = char
                    prevCharCount = 1
                }
            }
        }
        if (prevChar != null)
            yield(prevChar to prevCharCount)
    }
    return sequence.toLookAndSaySequence()
        .joinToString("") { (char, repeatCount) ->
            "$repeatCount$char"
        }
}