package com.github.bekirev.aoc.year2015.day01

import com.github.bekirev.aoc.year2015.day01.VerticalDirection.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Year2015Day01Test : ShouldSpec({
    should("determine a floor") {
        forAll(
            row("(())", "0"),
            row("()()", "0"),
            row("(((", "3"),
            row("(()(()(", "3"),
            row("))(((((", "3"),
            row("())", "-1"),
            row("))(", "-1"),
            row(")))", "-3"),
            row(")())())", "-3"),
        ) { input, result ->
            Day01.first(input) shouldBe result
        }
    }
    should("convert char to direction") {
        '('.toVerticalDirection() shouldBe UP
        ')'.toVerticalDirection() shouldBe DOWN
        shouldThrow<IllegalArgumentException> { 'a'.toVerticalDirection() }
    }
    should("parse string of parenthesis into a sequence of directions") {
        forAll(
            row("", emptyList()),
            row("(", listOf(UP)),
            row("((", listOf(UP, UP)),
            row(")", listOf(DOWN)),
            row("()()", listOf(UP, DOWN, UP, DOWN)),
        ) { input, resultList ->
            input.verticalDirections().toList() shouldBe resultList
        }
    }
    should("charge a floor according to given move directions") {
        forAll(
            row(0, emptySequence(), 0),
            row(1, emptySequence(), 1),
            row(0, sequenceOf(UP), 1),
            row(0, sequenceOf(UP, UP), 2),
            row(3, sequenceOf(DOWN), 2),
            row(2, sequenceOf(UP, DOWN, UP, UP, UP, DOWN), 4),
        ) { startFloor, moveDirections, endFloor ->
            moveDirections.move(startFloor) shouldBe endFloor
        }
    }
    should("determine position of the first character that result in -1 floor") {
        forAll(
            row(")", "1"),
            row("()())", "5"),
            row("((())))", "7")
        ) { input, result ->
            Day01.second(input) shouldBe result
        }
    }
    should("determine index of the first instruction in sequence that result in -1 floor") {
        forAll(
            row(0, sequenceOf(DOWN), 0),
            row(1, sequenceOf(DOWN, DOWN), 1),
            row(0, sequenceOf(UP, UP, DOWN, DOWN, DOWN), 4),
            row(0, sequenceOf(UP, DOWN, UP, DOWN, UP, DOWN, DOWN, DOWN, UP, UP, UP), 6),
            row(0, sequenceOf(UP, DOWN, UP, DOWN, UP, DOWN, UP, DOWN, UP, UP,  DOWN, DOWN, UP), -1),
        ) { startFloor, directionSequence, position ->
            directionSequence.firstIndexLeadsToBasementLevel(startFloor) shouldBe position
        }
    }
})