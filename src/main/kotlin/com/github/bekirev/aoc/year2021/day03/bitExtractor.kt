package com.github.bekirev.aoc.year2021.day03

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private val bitMasks: ConcurrentMap<Index, Int> = ConcurrentHashMap()

fun Int.bitAt(index: Int): Bit {
    val bitMask = bitMasks.computeIfAbsent(index) { 1 shl index }
    return if (this and bitMask == bitMask) Bit.ONE
    else Bit.ZERO
}
