package com.github.bekirev.aoc.year2015.day05

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly

class Year2015Day05Test : ShouldSpec({
    should("determine number of nice strings") {
        val input = """
            ugknbfddgicrmopn
            aaa
            jchzalrnumimnmhp
            haegwjzuvuyypxyu
            dvszwmarrgswjxmb
            """.trimIndent()
        Year2015Day05.first(input) shouldBeExactly 2
    }
    should("count nice strings acoording to the second set of rules") {
        val input = """
            qjhvhtzxzqqjkmpb
            xxyxx
            uurcxstgmygtbstg
            ieodomkazucvgmuy
            aaa
            aaaa
            """.trimIndent()
        Year2015Day05.second(input) shouldBeExactly 3
    }
})