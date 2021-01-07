package com.github.bekirev.aoc.year2015.day04

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun main() = Day.run(Year2015Day04)

object Year2015Day04 : Year2015Day(4) {
    override fun first(input: String): Int {
        val secretKey = input.trim()
        return generateSequence(1, 1::plus)
            .first { number ->
                (secretKey + number).md5().startsWith("00000")
            }
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