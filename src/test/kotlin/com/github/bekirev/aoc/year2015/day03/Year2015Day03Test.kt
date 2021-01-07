package com.github.bekirev.aoc.year2015.day03

import com.github.bekirev.aoc.utils.Position2D
import com.github.bekirev.aoc.year2015.day03.Direction2D.EAST
import com.github.bekirev.aoc.year2015.day03.Direction2D.NORTH
import com.github.bekirev.aoc.year2015.day03.Direction2D.SOUTH
import com.github.bekirev.aoc.year2015.day03.Direction2D.WEST
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class Year2015Day03Test : ShouldSpec({
    should("determine number of positions visited") {
        forAll(
            row("", 1),
            row(">", 2),
            row("^>v<", 4),
            row("^v^v^v^v^v", 2),
            row("^>^>vv<<^^", 9),
        ) { input, result ->
            Year2015Day03.first(input) shouldBeExactly result
        }
    }
    should("convert char to direction") {
        forAll(
            row('^', NORTH),
            row('v', SOUTH),
            row('>', EAST),
            row('<', WEST),
        ) { char, direction ->
            char.toDirection2D() shouldBe direction
        }
    }
    should("convert string to sequence of directions") {
        forAll(
            row("", emptyList()),
            row(">", listOf(EAST)),
            row("><", listOf(EAST, WEST)),
            row("vv<^>", listOf(SOUTH, SOUTH, WEST, NORTH, EAST)),
        ) { str, directions ->
            str.asDirection2DSequence().toList() shouldBe directions
        }
    }
    should("move position according to given direction") {
        forAll(
            row(Position2D(0, 0), NORTH, Position2D(0, 1)),
            row(Position2D(2, 1), NORTH, Position2D(2, 2)),
            row(Position2D(-1, 2), SOUTH, Position2D(-1, 1)),
            row(Position2D(0, 0), SOUTH, Position2D(0, -1)),
            row(Position2D(0, 0), EAST, Position2D(1, 0)),
            row(Position2D(5, 10), EAST, Position2D(6, 10)),
            row(Position2D(3, 2), WEST, Position2D(2, 2)),
            row(Position2D(0, 0), WEST, Position2D(-1, 0)),
        ) { position, direction, result ->
            position.move(direction) shouldBe result
        }
    }
    should("build a sequence of all visited positions") {
        forAll(
            row(
                Position2D(1, 2),
                emptySequence(),
                listOf(Position2D(1, 2))
            ),
            row(
                Position2D(3, 2),
                sequenceOf(WEST),
                listOf(Position2D(3, 2), Position2D(2, 2))
            ),
            row(
                Position2D(1, 1),
                sequenceOf(NORTH, EAST, SOUTH, WEST),
                listOf(Position2D(1, 1), Position2D(1, 2), Position2D(2, 2), Position2D(2, 1), Position2D(1, 1))
            ),
        ) { startPosition, directions, positions ->
            startPosition.positions(directions).toList() shouldBe positions
        }
    }
    should("determine number of positions visited by odd and even directions separately") {
        forAll(
            row("", 1),
            row("^v", 3),
            row("^>v<", 3),
            row("^v^v^v^v^v", 11),
            row("^>^>vv<<^^", 9),
        ) { input, result ->
            Year2015Day03.second(input) shouldBeExactly result
        }
    }
})