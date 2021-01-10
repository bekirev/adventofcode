package com.github.bekirev.aoc.year2015.day08

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day08)

object Year2015Day08 : Year2015Day(8) {
    private val literalsLength by lazy { input.lineSequence().filter(String::isNotBlank).sumBy(String::length) }

    override fun first(input: String): Int {
        val lengthInMemory = input.lineSequence().filter(String::isNotBlank).sumBy(String::memoryStringLength)
        return literalsLength - lengthInMemory
    }

    override fun second(input: String): Int {
        val encodedLength = input.lineSequence().filter(String::isNotBlank).sumBy(String::encodedLength)
        return encodedLength - literalsLength
    }
}

private val SYMBOL_CODE_REGEX = Regex("""\\x([0-9a-f]{2})""")
fun String.memoryStringLength(): Int =
    substring(1, lastIndex)
        .replace("\\\"", "?")
        .replace("\\\\", "?")
        .replace(SYMBOL_CODE_REGEX, "?")
        .length

fun String.encodedLength(): Int {
    val preEncoded = replace("\"", "??").replace("\\", "??")
    return "\"$preEncoded\"".length
}
