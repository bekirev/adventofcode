package com.github.bekirev.aoc.year2015.day02

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class Year2015Day02Test : ShouldSpec({
    should("calculate total wrapping paper for boxes") {
        forAll(
            row(
                """
                    |2x3x4
                    |1x1x10
                    |10x1x2
                """.trimMargin(),
                167
            )
        ) { boxStr, totalWrappingPaper ->
            Year2015Day02.first(boxStr) shouldBe totalWrappingPaper
        }
    }
    should("parse box from string") {
        forAll(
            row("2x3x4", Box(2, 3, 4)),
            row("1x1x10", Box(1, 1, 10)),
            row("10x1x1", Box(10, 1, 1)),
        ) { input, box ->
            input.toBox() shouldBe box
        }
    }
    should("calculate total wrapping paper for a box") {
        forAll(
            row(Box(2, 3, 4), 58),
            row(Box(1, 1, 10), 43),
            row(Box(10, 1, 2), 66),
        ) { box, totalWrappingPaper ->
            box.totalWrappingPaperNeeded() shouldBe totalWrappingPaper
        }
    }
    should("calculate surface area") {
        forAll(
            row(Box(2, 3, 4), 52),
            row(Box(1, 1, 10), 42),
            row(Box(10, 1, 2), 64),
        ) { box, surfaceArea ->
            box.surfaceArea shouldBeExactly surfaceArea
        }
    }
    should("calculate surface of the smallest side") {
        forAll(
            row(Box(2, 3, 4), 6),
            row(Box(1, 1, 10), 1),
            row(Box(10, 1, 2), 2),
        ) { box, surfaceArea ->
            box.smallestSideArea shouldBeExactly surfaceArea
        }
    }
    should("calculate total ribbon for boxes") {
        forAll(
            row(
                """
                    |2x3x4
                    |1x1x10
                    |10x1x2
                """.trimMargin(),
                74
            )
        ) { boxStr, totalWrappingPaper ->
            Year2015Day02.second(boxStr) shouldBe totalWrappingPaper
        }
    }
    should("calculate total ribbon for a box") {
        forAll(
            row(Box(2, 3, 4), 34),
            row(Box(1, 1, 10), 14),
            row(Box(10, 1, 2), 26),
        ) { box, totalRibbon ->
            box.totalRibbonNeeded() shouldBeExactly totalRibbon
        }
    }
    should("calculate the smallest perimeter") {
        forAll(
            row(Box(2, 3, 4), 10),
            row(Box(1, 1, 10), 4),
            row(Box(10, 1, 2), 6),
        ) { box, surfacePerimeter ->
            box.smallestPerimeter shouldBeExactly surfacePerimeter
        }
    }
    should("calculate volume of the box") {
        forAll(
            row(Box(2, 3, 4), 24),
            row(Box(1, 1, 10), 10),
            row(Box(10, 1, 2), 20),
        ) { box, volume ->
            box.volume shouldBeExactly volume
        }
    }
})