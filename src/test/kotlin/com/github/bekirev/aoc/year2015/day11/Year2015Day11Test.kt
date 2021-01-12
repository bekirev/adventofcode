package com.github.bekirev.aoc.year2015.day11

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Year2015Day11Test : ShouldSpec({
    should("find next valid password after given one") {
        forAll(
            row("abcdefgh", "abcdffaa"),
            row("ghijklmn", "ghjaabcc"),
        ) { password, nextValidPassword ->
            Year2015Day11.first(password) shouldBe nextValidPassword
        }
    }
    should("validate password") {
        forAll(
            row("", false),
            row("aabcc", true),
            row("abcdffaa", true),
            row("ghjaabcc", true),
        ) { password, validationResult ->
            password.isValid() shouldBe validationResult
        }
    }
})