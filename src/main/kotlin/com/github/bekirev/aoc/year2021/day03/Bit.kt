package com.github.bekirev.aoc.year2021.day03

enum class Bit {
    ZERO,
    ONE,
    ;

    fun flip(): Bit = when (this) {
        ZERO -> ONE
        ONE -> ZERO
    }

    fun toChar(): Char = when (this) {
        ZERO -> '0'
        ONE -> '1'
    }
}
