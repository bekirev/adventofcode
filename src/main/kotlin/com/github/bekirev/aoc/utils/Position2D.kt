package com.github.bekirev.aoc.utils

data class Position2D(
    val x: Int,
    val y: Int,
) {
    companion object {
        val ZERO = Position2D(0, 0)
    }
}