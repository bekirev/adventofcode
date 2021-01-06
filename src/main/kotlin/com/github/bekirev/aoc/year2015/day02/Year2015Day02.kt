package com.github.bekirev.aoc.year2015.day02

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day02)

object Year2015Day02 : Year2015Day(2) {
    override fun first(input: String): Int =
        input.splitToSequence("\n")
            .filter(String::isNotBlank)
            .map(String::toBox)
            .map(Box::totalWrappingPaperNeeded)
            .sum()

    override fun second(input: String): Int =
        input.splitToSequence("\n")
            .filter(String::isNotBlank)
            .map(String::toBox)
            .map(Box::totalRibbonNeeded)
            .sum()
}

fun String.toBox(): Box {
    val (length, width, height) = split("x")
    return Box(length.toInt(), width.toInt(), height.toInt())
}

data class Box(
    val length: Int,
    val width: Int,
    val height: Int,
) {
    val surfaceArea: Int by lazy {
        2 * length * width + 2 * width * height + 2 * height * length
    }
    private val twoSmallestSides: List<Int> by lazy {
        mutableListOf(length, width, height).apply {
            remove(maxOrNull()!!)
        }
    }
    val smallestSideArea: Int by lazy { twoSmallestSides.reduce(Int::times) }
    val smallestPerimeter: Int by lazy { 2 * twoSmallestSides.reduce(Int::plus) }
    val volume: Int by lazy { length * width * height }
}

fun Box.totalWrappingPaperNeeded(): Int = surfaceArea + smallestSideArea
fun Box.totalRibbonNeeded(): Int = smallestPerimeter + volume
