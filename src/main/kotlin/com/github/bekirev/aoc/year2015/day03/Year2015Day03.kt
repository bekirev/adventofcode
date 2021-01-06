package com.github.bekirev.aoc.year2015.day03

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import com.github.bekirev.aoc.year2015.day03.Direction2D.EAST
import com.github.bekirev.aoc.year2015.day03.Direction2D.NORTH
import com.github.bekirev.aoc.year2015.day03.Direction2D.SOUTH
import com.github.bekirev.aoc.year2015.day03.Direction2D.WEST

fun main() = Day.run(Year2015Day03)

object Year2015Day03 : Year2015Day(3) {
    override fun first(input: String): Int =
        Position2D.ZERO.positions(input.asDirection2DSequence()).distinct().count()

    override fun second(input: String): Int =
        sequenceOf(
            Position2D.ZERO.positions(input.asDirection2DSequence().even()),
            Position2D.ZERO.positions(input.asDirection2DSequence().odd()),
        ).flatten().distinct().count()
}

enum class Direction2D {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    ;
}

fun Char.toDirection2D(): Direction2D = when (this) {
    '^' -> NORTH
    'v' -> SOUTH
    '>' -> EAST
    '<' -> WEST
    else -> throw IllegalArgumentException()
}

fun String.asDirection2DSequence(): Sequence<Direction2D> =
    asSequence().map(Char::toDirection2D)

data class Position2D(
    val x: Int,
    val y: Int,
) {
    companion object {
        val ZERO = Position2D(0, 0)
    }
}

fun Position2D.move(direction2D: Direction2D): Position2D = when (direction2D) {
    NORTH -> copy(y = y + 1)
    SOUTH -> copy(y = y - 1)
    EAST -> copy(x = x + 1)
    WEST -> copy(x = x - 1)
}

fun Position2D.positions(directions: Sequence<Direction2D>): Sequence<Position2D> = sequence {
    var position = this@positions
    yield(position)
    for (direction in directions) {
        position = position.move(direction)
        yield(position)
    }
}

fun <T> Sequence<T>.even(): Sequence<T> = sequence {
    var i = 0L
    for (elem in this@even) {
        if (i % 2 == 0L)
            yield(elem)
        ++i
    }
}

fun <T> Sequence<T>.odd(): Sequence<T> = sequence {
    var i = 0L
    for (elem in this@odd) {
        if (i % 2 == 1L)
            yield(elem)
        ++i
    }
}