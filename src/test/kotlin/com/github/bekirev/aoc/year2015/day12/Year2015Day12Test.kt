package com.github.bekirev.aoc.year2015.day12

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldBeExactly

class Year2015Day12Test : ShouldSpec({
    should("sum all the numbers from json object") {
        forAll(
            row("[]", 0),
            row("{}", 0),
            row("[-1,{\"a\":1}]", 0),
            row("{\"a\":[-1,1]}", 0),
            row("[[[3]]]", 3),
            row("{\"a\":{\"b\":4},\"c\":-1}", 3),
            row("[1,2,3]", 6),
            row("{\"a\":2,\"b\":4}", 6),
        ) { input, result ->
            Year2015Day12.first(input) shouldBeExactly result
        }
    }
})