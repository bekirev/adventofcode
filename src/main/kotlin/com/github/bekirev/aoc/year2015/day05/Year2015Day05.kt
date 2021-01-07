package com.github.bekirev.aoc.year2015.day05

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import java.util.LinkedList

fun main() = Day.run(Year2015Day05)

object Year2015Day05 : Year2015Day(5) {
    override fun first(input: String): Int {
        return input.splitToSequence("\n")
            .filter(String::isNotBlank)
            .count(String::isNice)
    }
}

fun String.isNice(): Boolean = containsAtLeastThreeVowels() &&
        containsRepeatingTwiceInARowLetter() &&
        !containsUglyStrings()

fun String.containsAtLeastThreeVowels(): Boolean {
    var vowelsCount = 0
    for (char in this) {
        if (char.isVowel()) ++vowelsCount
        if (vowelsCount >= 3) return true
    }
    return false
}

fun Char.isVowel(): Boolean = this in setOf('a', 'e', 'i', 'o', 'u')

fun String.containsRepeatingTwiceInARowLetter(): Boolean {
    var prev: Char? = null
    for (char in this) {
        if (char == prev) return true
        prev = char
    }
    return false
}

val uglyStrings = setOf("ab", "cd", "pq", "xy")

fun String.containsUglyStrings(): Boolean {
    val sizes = uglyStrings.asSequence().map { it.length }.toSet()
    val maxSize = sizes.maxOrNull() ?: return false
    val currentSubStringsOfSize = mutableMapOf<Int, String>()
    val prevChars = LinkedList<Char>()
    fun fillSubstrings() {
        currentSubStringsOfSize.clear()
        val sb = StringBuilder()
        for (c in prevChars) {
            sb.append(c)
            if (sb.length in sizes)
                currentSubStringsOfSize[sb.length] = sb.toString()
        }
    }
    for (char in this) {
        when (prevChars.size) {
            maxSize -> {
                prevChars.remove()
                prevChars.add(char)
            }
            else -> prevChars.add(char)
        }
        fillSubstrings()
        if (uglyStrings.any(currentSubStringsOfSize::containsValue))
            return true
    }
    return false
}