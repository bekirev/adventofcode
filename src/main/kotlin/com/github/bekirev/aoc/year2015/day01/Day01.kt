package com.github.bekirev.aoc.year2015.day01

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import com.github.bekirev.aoc.year2015.day01.VerticalDirection.DOWN
import com.github.bekirev.aoc.year2015.day01.VerticalDirection.UP

fun main() = Day.run(Day01)

object Day01 : Year2015Day(1) {
    override fun first(input: String): String =
        input.verticalDirections().move(0).toString()
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
