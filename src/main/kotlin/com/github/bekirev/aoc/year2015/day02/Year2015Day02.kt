package com.github.bekirev.aoc.year2015.day02

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day02)

object Year2015Day02 : Year2015Day(2) {
    override fun first(input: String): Int =
        input.splitToSequence("\n")
            .filter { it.isNotBlank() }
            .map { it.toBox() }
            .map { it.totalWrappingPaperNeeded }
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
    val smallestSideArea: Int by lazy {
        val sides = mutableListOf(length, width, height)
        val firstMin = sides.minOrNull()!!
        sides.remove(firstMin)
        val secondMin = sides.minOrNull()!!
        firstMin * secondMin
    }
}

val Box.totalWrappingPaperNeeded: Int
    get() = surfaceArea + smallestSideArea
