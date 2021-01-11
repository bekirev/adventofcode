package com.github.bekirev.aoc.year2015.day09

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly

class Year2015Day09Test : ShouldSpec({
    should("find the distance of the shortest route from raw input") {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent()
        Year2015Day09.first(input) shouldBeExactly 605
    }

    should("find the distance of the longest route from raw input") {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent()
        Year2015Day09.second(input) shouldBeExactly 982
    }
})