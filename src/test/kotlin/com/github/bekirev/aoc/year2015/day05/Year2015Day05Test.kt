package com.github.bekirev.aoc.year2015.day05

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

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
    should("determine whether a string is nice") {
        forAll(
            row("ugknbfddgicrmopn", true),
            row("aaa", true),
            row("jchzalrnumimnmhp", false),
            row("haegwjzuvuyypxyu", false),
            row("dvszwmarrgswjxmb", false),
        ) { str, nice ->
            str.isNice() shouldBe nice
        }
    }
    should("check whether a string contains at least three vowels") {
        forAll(
            row("", false),
            row("a", false),
            row("nklseninm", false),
            row("amkomsdnudsjk", true),
            row("eua", true),
            row("bnsajiokasju", true),
        ) { str, result ->
            str.containsAtLeastThreeVowels() shouldBe result
        }
    }
    should("check whether a string contains repeating twice in a row letter") {
        forAll(
            row("", false),
            row("j", false),
            row("as", false),
            row("gg", true),
            row("asdbjnkkfsa", true)
        ) { str, result ->
            str.containsRepeatingTwiceInARowLetter() shouldBe result
        }
    }
})