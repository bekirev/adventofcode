package com.github.bekirev.aoc.utils.grid

import com.github.bekirev.aoc.utils.Position2D

interface MutableGrid<T> : Grid<T> {
    operator fun set(position: Position2D, value: T)
    fun set(row: Int): (Int, T) -> Unit
}