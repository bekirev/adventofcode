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
    override fun first(input: String): Int =
        finalGrid(input, SimpleStateChanger)
            .getAll()
            .count { it.value == 1 }

    override fun second(input: String): Int =
        finalGrid(input, BrightnessStateChanger)
            .getAll()
            .sumBy { it.value }

    private fun finalGrid(input: String, stateChanger: StateChanger): MutableGrid<LightState> {
        val size = Size(1000, 1000)
        val grid: MutableGrid<LightState> = ArrayListMutableGrid(size) { 0 }
        input
            .lineSequence()
            .filter(String::isNotBlank)
            .map(String::toLightCommand)
            .forEach { execute(it, grid, stateChanger) }
        return grid
    }
}

fun execute(command: LightCommand, grid: MutableGrid<LightState>, stateChanger: StateChanger) {
    for (row in command.rowRange) {
        val getByColFun = grid[row]
        val setByColFun = grid.set(row)
        for (col in command.colRange) {
            setByColFun(col, stateChanger.stateAfter(getByColFun(col), command.action))
        }
    }
}

typealias LightState = Int

data class LightCommand(val action: LightAction, val rowRange: IntRange, val colRange: IntRange)

enum class LightAction {
    TURN_ON,
    TURN_OFF,
    TOGGLE,
    ;
}

interface StateChanger {
    fun stateAfter(state: LightState, action: LightAction): LightState
}

object SimpleStateChanger : StateChanger {
    override fun stateAfter(state: LightState, action: LightAction): LightState = when (action) {
        TURN_ON -> 1
        TURN_OFF -> 0
        TOGGLE -> when (state) {
            0 -> 1
            else -> 0
        }
    }
}

object BrightnessStateChanger : StateChanger {
    override fun stateAfter(state: LightState, action: LightAction): LightState = when (action) {
        TURN_ON -> state + 1
        TURN_OFF -> when (state) {
            0 -> 0
            else -> state - 1
        }
        TOGGLE -> state + 2
    }
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