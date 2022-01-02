package com.github.bekirev.aoc.year2021.day01

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Year2021Day01Test : ShouldSpec({
    should(
        """count the depth change speed based on
           the number of times a depth measurement
           increases""".trimIndent()
    ) {
        val input = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent()
        Year2021Day01.first(input) shouldBe 7
    }

    should(
        """count the depth change speed based on
           the number of times the sum of measurements
           in this sliding window increases""".trimIndent()
    ) {
        val input = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent()
        Year2021Day01.second(input) shouldBe 5
    }
})
