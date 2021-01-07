package com.github.bekirev.aoc.year2015.day06

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.utils.grid.ArrayListMutableGrid
import com.github.bekirev.aoc.utils.grid.MutableGrid
import com.github.bekirev.aoc.utils.grid.Size
import com.github.bekirev.aoc.year2015.Year2015Day
import com.github.bekirev.aoc.year2015.day06.LightAction.TOGGLE
import com.github.bekirev.aoc.year2015.day06.LightAction.TURN_OFF
import com.github.bekirev.aoc.year2015.day06.LightAction.TURN_ON

fun main() = Day.run(Year2015Day06)

object Year2015Day06 : Year2015Day(6) {
    override fun first(input: String): Int {
        val size = Size(1000, 1000)
        val grid: MutableGrid<LightState> = ArrayListMutableGrid(size) { false }
        input
            .lineSequence()
            .filter(String::isNotBlank)
            .map(String::toLightCommand)
            .forEach { execute(it, grid) }
        return grid.getAll().count { it.value }
    }
}

fun execute(command: LightCommand, grid: MutableGrid<LightState>) {
    for (row in command.rowRange) {
        val getByColFun = grid[row]
        val setByColFun = grid.set(row)
        for (col in command.colRange) {
            setByColFun(col, command.action.stateAfter(getByColFun(col)))
        }
    }
}

typealias LightState = Boolean

data class LightCommand(val action: LightAction, val rowRange: IntRange, val colRange: IntRange)

enum class LightAction(val stateAfter: (LightState) -> LightState) {
    TURN_ON({ true }),
    TURN_OFF({ false }),
    TOGGLE({ state -> !state }),
    ;
}

fun String.toLightCommand(): LightCommand {
    val (action, rangesStr) = when {
        startsWith("turn on ") -> TURN_ON to substringAfter("turn on ")
        startsWith("turn off ") -> TURN_OFF to substringAfter("turn off ")
        startsWith("toggle ") -> TOGGLE to substringAfter("toggle ")
        else -> throw IllegalArgumentException()
    }
    val (leftPart, _, rightPart) = rangesStr.split(" ")
    val (leftRowStr, leftColStr) = leftPart.split(",")
    val (rightRowStr, rightColStr) = rightPart.split(",")
    return LightCommand(
        action,
        leftRowStr.toInt()..rightRowStr.toInt(),
        leftColStr.toInt()..rightColStr.toInt(),
    )
}