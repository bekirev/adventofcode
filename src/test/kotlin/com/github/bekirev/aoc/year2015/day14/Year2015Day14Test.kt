package com.github.bekirev.aoc.year2015.day14

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly

class Year2015Day14Test : ShouldSpec({
    should("move reindeer") {
        val reindeer = Reindeer(
            "Calvin",
            17,
            3,
            5
        )
        reindeer.position shouldBeExactly 0
        for (time in 1..16) {
            reindeer.move()
            when (time) {
                in 1..3 -> reindeer.position shouldBeExactly 17 * time
                in 4..8 -> reindeer.position shouldBeExactly 17 * 3
                in 9..11 -> reindeer.position shouldBeExactly 17 * (time - 5)
                in 12..16 -> reindeer.position shouldBeExactly 17 * 6
            }
        }
    }
})