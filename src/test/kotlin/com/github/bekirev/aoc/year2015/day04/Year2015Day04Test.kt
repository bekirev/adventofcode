package com.github.bekirev.aoc.year2015.day04

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class Year2015Day04Test : ShouldSpec({
    should("find the lowest positive number that produces a md5 hash with five leading zeros") {
        forAll(
            row("abcdef", 609043),
            row("pqrstuv", 1048970),
        ) { secretKey, result ->
            Year2015Day04.first(secretKey) shouldBeExactly result
        }
    }
    should("calculate md5") {
        forAll(
            row("dsdouir532", "5397075055f1ab03291ce233ba1ba059"),
            row("421dsaokj1nf", "e5a29927829e8fad46d848f963ffe703"),
        ) { input, md5Hash ->
            input.md5() shouldBe md5Hash
        }
    }
})