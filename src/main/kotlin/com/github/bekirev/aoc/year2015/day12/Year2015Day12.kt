package com.github.bekirev.aoc.year2015.day12

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull

fun main() = Day.run(Year2015Day12)

object Year2015Day12 : Year2015Day(12) {
    override fun first(input: String): Int {
        return Json.parseToJsonElement(input.trim()).sumNumbers()
    }

    override fun second(input: String): Int {
        return Json.parseToJsonElement(input.trim()).sumNumbers {
            !containsValue(JsonPrimitive("red"))
        }
    }

    private fun JsonElement.sumNumbers(): Int = when (this) {
        is JsonPrimitive -> contentOrNull?.toIntOrNull() ?: 0
        is JsonObject -> values.sumBy { it.sumNumbers() }
        is JsonArray -> sumBy { it.sumNumbers() }
    }

    private fun JsonElement.sumNumbers(isValid: JsonObject.() -> Boolean): Int = when (this) {
        is JsonPrimitive -> contentOrNull?.toIntOrNull() ?: 0
        is JsonObject -> {
            if (isValid()) values.sumBy { it.sumNumbers(isValid) }
            else 0
        }
        is JsonArray -> sumBy { it.sumNumbers(isValid) }
    }
}