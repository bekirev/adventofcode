package com.github.bekirev.aoc.year2015.day11

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import com.github.bekirev.aoc.year2015.day11.AlphabetNumber.Companion.ONE

fun main() = Day.run(Year2015Day11)

object Year2015Day11 : Year2015Day(11) {
    private const val PASSWORD_LENGTH = 8
    override fun first(input: String): String =
        generateSequence(AlphabetNumber(input.trim())) { number ->
            val original = number.toString(PASSWORD_LENGTH)
            when (val badLetterIndex = original.indexOfFirst(BAD_LETTERS::contains)) {
                -1 -> number + ONE
                else -> AlphabetNumber(
                    original.substring(0, badLetterIndex) +
                            (original[badLetterIndex] + 1).toString() +
                            "a".repeat(original.lastIndex - badLetterIndex)
                )
            }
        }
            .map { it.toString(PASSWORD_LENGTH) }
            .first(String::isValid)
}

typealias Password = String

fun Password.isValid(): Boolean =
    !containsBadLetters() &&
            containsOneIncreasingStraight(3) &&
            containsTwoDifferentPairsOfLetters()

private val BAD_LETTERS = setOf('i', 'o', 'l')

fun Password.containsBadLetters(): Boolean =
    any(BAD_LETTERS::contains)

fun Password.containsOneIncreasingStraight(letterCount: Int): Boolean =
    if (letterCount <= length)
        (0..length - letterCount)
            .asSequence()
            .map { start ->
                subSequence(start, start + letterCount)
            }
            .any { sequence ->
                val first: Char = sequence.first()
                for (char in sequence.asSequence().mapIndexed { index, char -> char - index }.drop(1)) {
                    if (char != first)
                        return@any false
                }
                true
            }
    else
        false

private val AA_REGEX = "([a-z])\\1".toRegex()

fun Password.containsTwoDifferentPairsOfLetters(): Boolean =
    AA_REGEX.findAll(this)
        .mapNotNull { match ->
            match.groups[1]?.value
        }
        .distinct()
        .count() > 1