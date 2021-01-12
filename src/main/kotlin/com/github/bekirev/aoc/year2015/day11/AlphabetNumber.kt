package com.github.bekirev.aoc.year2015.day11

import java.math.BigInteger

class AlphabetNumber(
    private val value: BigInteger,
) {
    companion object {
        val ONE = AlphabetNumber(BigInteger.ONE)
        private const val RADIX = 26
        private val toAlphabetMap: Map<Char, Char> by lazy {
            (0..RADIX).asSequence()
                .map { it.toString(RADIX).first() }
                .zip(('a'..'z').asSequence())
                .toMap()
        }
        private val toNumberAndAlphabetMap: Map<Char, Char> by lazy {
            ('a'..'z')
                .asSequence()
                .zip((0..RADIX).asSequence().map { it.toString(RADIX).first() })
                .toMap()
        }

        private fun String.toAlphabet(): String =
            asSequence()
                .map { char -> toAlphabetMap[char]!! }
                .joinToString("")

        private fun String.toNumberAndAlphabet(): String =
            asSequence()
                .map { char -> toNumberAndAlphabetMap[char]!! }
                .joinToString("")
    }

    constructor(str: String) : this(
        BigInteger(
            str.toNumberAndAlphabet(),
            RADIX
        )
    )

    operator fun plus(other: AlphabetNumber): AlphabetNumber =
        AlphabetNumber(
            value + other.value
        )

    override fun toString(): String =
        value.toString(26).toAlphabet()

    fun toString(digitsCount: Int): String {
        val str = toString()
        val symbolsLeft = digitsCount - str.length
        return if (symbolsLeft > 0) "a".repeat(symbolsLeft) + str
        else str
    }
}