package com.github.bekirev.aoc.year2015.day06

import com.github.bekirev.aoc.year2015.day06.LightAction.TOGGLE
import com.github.bekirev.aoc.year2015.day06.LightAction.TURN_OFF
import com.github.bekirev.aoc.year2015.day06.LightAction.TURN_ON
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Year2015Day06 : ShouldSpec({
    should("Determine next state") {
        forAll(
            row(TURN_ON, false, true),
            row(TURN_ON, true, true),
            row(TURN_OFF, false, false),
            row(TURN_OFF, true, false),
            row(TOGGLE, false, true),
            row(TOGGLE, true, false),
        ) { action, state, result ->
            action.stateAfter(state) shouldBe result
        }
    }
})