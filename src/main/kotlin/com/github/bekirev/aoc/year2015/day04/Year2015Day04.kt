package com.github.bekirev.aoc.year2015.day04

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun main() = Day.run(Year2015Day04)

object Year2015Day04 : Year2015Day(4) {
    override fun first(input: String): Int {
        return result(input, 5)
    }

    override fun second(input: String): Int {
        return result(input, 6)
    }

    private fun result(input: String, leadingZerosCount: Int): Int {
        val secretKey = input.trim()
        return firstMd5HashWithLeadingZeros(secretKey, leadingZerosCount)
    }
}

fun firstMd5HashWithLeadingZeros(secretKey: String, leadingZerosCount: Int): Int {
    val prefix = "0".repeat(leadingZerosCount)
    return generateSequence(1, 1::plus)
        .first { number ->
            (secretKey + number).md5().startsWith(prefix)
        }
}

fun String.md5(): String =
    "%032x".format(
        BigInteger(
            1,
            MessageDigest
                .getInstance("MD5")
                .digest(toByteArray(StandardCharsets.UTF_8))
        )
    )