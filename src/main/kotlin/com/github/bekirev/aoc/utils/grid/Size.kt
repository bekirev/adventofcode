package com.github.bekirev.aoc.utils.grid

import com.github.bekirev.aoc.utils.Position2D

data class Size(
    val colCount: Int,
    val rowCount: Int,
)

fun Size.positions(): Sequence<Position2D> =
    (0 until rowCount).asSequence().flatMap { row ->
        (0 until colCount).asSequence().map { col ->
            Position2D(row, col)
        }
    }