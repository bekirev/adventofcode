package com.github.bekirev.aoc.year2015.day10

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Year2015Day10Test : ShouldSpec({
    should("perform multiple iterations of look-and-say game") {
        forAll(
            row("1", 0, "1"),
            row("1", 1, "11"),
            row("1", 2, "21"),
            row("21", 3, "312211"),
            row("1", 5, "312211"),
        ) { inputSequence, times, result ->
            lookAndSay(inputSequence, times) shouldBe result
        }
    }
    should("perform one iteration of look-and-say game") {
        forAll(
            row("1", "11"),
            row("11", "21"),
            row("21", "1211"),
            row("1211", "111221"),
            row("111221", "312211"),
        ) { input, result ->
            lookAndSay(input) shouldBe result
        }
    }
})