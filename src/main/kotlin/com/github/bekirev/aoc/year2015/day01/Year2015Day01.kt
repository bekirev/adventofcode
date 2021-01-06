package com.github.bekirev.aoc.year2015.day01

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import com.github.bekirev.aoc.year2015.day01.VerticalDirection.DOWN
import com.github.bekirev.aoc.year2015.day01.VerticalDirection.UP

fun main() = Day.run(Year2015Day01)

object Year2015Day01 : Year2015Day(1) {
    private const val START_FLOOR = 0

    override fun first(input: String): Int =
        input.verticalDirections().move(START_FLOOR)

    override fun second(input: String): Int =
        (input.verticalDirections().firstIndexLeadsToBasementLevel(START_FLOOR) + 1)
}

typealias Floor = Int

enum class VerticalDirection {
    UP,
    DOWN,
    ;
}

fun Char.toVerticalDirection(): VerticalDirection = when (this) {
    '(' -> UP
    ')' -> DOWN
    else -> throw IllegalArgumentException()
}

fun String.verticalDirections(): Sequence<VerticalDirection> =
    asSequence().map(Char::toVerticalDirection)

fun Sequence<VerticalDirection>.move(startFloor: Floor): Floor {
    var floor = startFloor
    for (direction in this) {
        when (direction) {
            UP -> ++floor
            DOWN -> --floor
        }
    }
    return floor
}

fun Sequence<VerticalDirection>.firstIndexLeadsToBasementLevel(startFloor: Floor): Int {
    var floor = startFloor
    for ((index, direction) in this.withIndex()) {
        when (direction) {
            UP -> ++floor
            DOWN -> --floor
        }
        if (floor == -1) return index
    }
    return -1
}