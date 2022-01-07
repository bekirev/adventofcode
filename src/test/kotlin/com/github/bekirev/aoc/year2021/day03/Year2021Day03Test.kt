package com.github.bekirev.aoc.year2021.day03

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Year2021Day03Test : ShouldSpec({
    val input = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent()
    should("calculate power consumption of the submarine") {
        Year2021Day03.first(input) shouldBe 198
    }
    should("calculate life support rating") {
        Year2021Day03.second(input) shouldBe 230
    }
})
