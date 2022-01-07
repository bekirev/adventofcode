package com.github.bekirev.aoc.year2021.day03

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2021.Year2021Day

fun main() = Day.run(Year2021Day03)

object Year2021Day03 : Year2021Day(3) {
    override fun first(input: String): Int {
        val length = input.elementLength()
        val bitCountByIndex = input.numbers()
            .fold(BitCounter(length)) { bitCounter, number ->
                bitCounter.account(number)
            }
            .bitCountByIndex
        (length - 1 downTo 0)
            .map { index -> bitCountByIndex.getValue(index) }
        val gammaRate = gammaRate(length, bitCountByIndex)
        val epsilonRate = epsilonRate(length, bitCountByIndex)
        return gammaRate * epsilonRate
    }

    override fun second(input: String): Int {
        val length = input.elementLength()
        val numbers = input.numbers().toList()
        val oxygenGeneratorRating = oxygenGeneratorRating(length, numbers)
        val co2ScrubberRating = co2ScrubberRating(length, numbers)
        return oxygenGeneratorRating * co2ScrubberRating
    }

    private fun String.elementLength() = lineSequence().first().length

    private fun String.numbers(): Sequence<Int> =
        lineSequence()
            .mapNotNull { it.toIntOrNull(radix = 2) }

    private class BitCounter(
        private val length: Int,
    ) {
        private var accountedCount = 0L
        private val indices: Sequence<Int>
            get() = (0 until length).asSequence()

        private val oneCountByIndex: MutableMap<Index, Count> = indices.associateWithTo(mutableMapOf()) { 0 }

        fun account(value: Int): BitCounter = apply {
            indices.forEach { index ->
                if (value.bitAt(index) == Bit.ONE)
                    oneCountByIndex[index] = oneCountByIndex.getValue(index) + 1
            }
            ++accountedCount
        }

        val bitCountByIndex: Map<Index, Map<Bit, Count>>
            get() = oneCountByIndex.entries.associate { (index, oneCount) ->
                index to mapOf(
                    Bit.ONE to oneCount,
                    Bit.ZERO to (accountedCount - oneCount),
                )
            }
    }

    private fun gammaRate(length: Int, bitCountByIndex: Map<Index, Map<Bit, Count>>): Int {
        val mostCommonBits = bitCountByIndex.mostCommonBits()
        return toInt(length, mostCommonBits)
    }

    private fun epsilonRate(length: Int, bitCountByIndex: Map<Index, Map<Bit, Long>>): Int {
        val leastCommonBits = bitCountByIndex.leastCommonBits()
        return toInt(length, leastCommonBits)
    }

    private fun oxygenGeneratorRating(length: Int, numbers: List<Int>): Int {
        tailrec fun oxygenGeneratorRating(index: Index, numbers: List<Int>): List<Int> = when {
            numbers.size == 1 || index == length -> numbers
            else -> {
                val mostCommonBitAtIndex = numbers.asSequence().mostCommonBitAt(index)
                oxygenGeneratorRating(
                    index = index - 1,
                    numbers = numbers.filter { number ->
                        number.bitAt(index) == mostCommonBitAtIndex
                    },
                )
            }
        }
        return oxygenGeneratorRating(index = length - 1, numbers = numbers).first()
    }

    private fun co2ScrubberRating(length: Int, numbers: List<Int>): Int {
        tailrec fun co2ScrubberRating(index: Index, numbers: List<Int>): List<Int> = when {
            numbers.size == 1 || index == length -> numbers
            else -> {
                val leastCommonBitAtIndex = numbers.asSequence().leastCommonBitAt(index)
                co2ScrubberRating(
                    index = index - 1,
                    numbers = numbers.filter { number ->
                        number.bitAt(index) == leastCommonBitAtIndex
                    },
                )
            }
        }
        return co2ScrubberRating(index = length - 1, numbers = numbers).first()
    }

    private fun toInt(
        length: Int,
        bits: Map<Index, Bit>,
    ): Int {
        val numberStrBuilder = StringBuilder()
        (length - 1 downTo 0)
            .map(bits::getValue)
            .map(Bit::toChar)
            .forEach(numberStrBuilder::append)
        return numberStrBuilder.toString().toInt(2)
    }

    private fun Map<Index, Map<Bit, Count>>.mostCommonBits(): Map<Index, Bit> =
        keys.associateWith { index ->
            getValue(index).mostCommonBit
        }

    private fun Map<Index, Map<Bit, Count>>.leastCommonBits(): Map<Index, Bit> =
        mostCommonBits().entries.associate { (index, bit) ->
            index to bit.flip()
        }

    private fun Sequence<Int>.mostCommonBitAt(index: Index): Bit =
        bitCountAt(index).mostCommonBit

    private fun Sequence<Int>.leastCommonBitAt(index: Index): Bit =
        bitCountAt(index).leastCommonBitAt

    private val Map<Bit, Count>.mostCommonBit: Bit
        get() = if (oneCount >= zeroCount) Bit.ONE
        else Bit.ZERO

    private val Map<Bit, Count>.leastCommonBitAt: Bit
        get() = mostCommonBit.flip()

    private val Map<Bit, Count>.oneCount: Count
        get() = getOrDefault(Bit.ONE, 0L)

    private val Map<Bit, Count>.zeroCount: Count
        get() = getOrDefault(Bit.ZERO, 0L)

    private fun Sequence<Int>.bitCountAt(index: Index): Map<Bit, Count> =
        this
            .map { it.bitAt(index) }
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, value) -> value.toLong() }
}
