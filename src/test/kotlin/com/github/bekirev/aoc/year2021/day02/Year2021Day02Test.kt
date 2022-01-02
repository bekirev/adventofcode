package com.github.bekirev.aoc.year2021.day02

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Year2021Day02Test : ShouldSpec({
    val input = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent()

    should("""
        figure out where the submarine is going
        """.trimIndent()) {
        Year2021Day02.first(input) shouldBe 150
    }

    should("""
        figure out where the submarine is going
        based on new commands interpretation
    """.trimIndent()) {
        Year2021Day02.second(input) shouldBe 900
    }
})
