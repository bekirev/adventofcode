package com.github.bekirev.aoc.utils.grid

import com.github.bekirev.aoc.utils.Position2D

interface Grid<T> {
    val size: Size
    operator fun get(position: Position2D): T
    operator fun get(row: Int): (Int) -> T
    fun getAll(): Sequence<PositionValue<T>>
}

data class PositionValue<T>(
    val position: Position2D,
    val value: T
)