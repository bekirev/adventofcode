package com.github.bekirev.aoc.year2015.day05

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day05)

object Year2015Day05 : Year2015Day(5) {
    private val AA_REGEX = "([a-z])\\1".toRegex()
    private val BAD_2_CHARS_REGEX = "ab|cd|pq|xy".toRegex()
    private val ABAB_REGEX = "([a-z]{2}).*\\1".toRegex()
    private val ABA_REGEX = "([a-z]).\\1".toRegex()

    override fun first(input: String): Int =
        count { string ->
            string.count { it in "aeiou" } >= 3 &&
                    AA_REGEX in string &&
                    BAD_2_CHARS_REGEX !in string
        }

    override fun second(input: String): Int =
        count { string ->
            ABAB_REGEX in string &&
                    ABA_REGEX in string
        }

    private fun count(
        predicate: (String) -> Boolean,
    ): Int =
        input
            .lineSequence()
            .filter(String::isNotBlank)
            .count(predicate)
}