package com.github.bekirev.aoc.year2015.day06

import com.github.bekirev.aoc.year2015.day06.LightAction.TOGGLE
import com.github.bekirev.aoc.year2015.day06.LightAction.TURN_OFF
import com.github.bekirev.aoc.year2015.day06.LightAction.TURN_ON
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Year2015Day06 : ShouldSpec({
    should("Determine next state with boolean-like state") {
        forAll(
            row(TURN_ON, 0, 1),
            row(TURN_ON, 1, 1),
            row(TURN_OFF, 0, 0),
            row(TURN_OFF, 1, 0),
            row(TOGGLE, 0, 1),
            row(TOGGLE, 1, 0),
        ) { action, state, result ->
            SimpleStateChanger.stateAfter(state, action) shouldBe result
        }
    }
    should("Determine next state with brightness state") {
        forAll(
            row(TURN_ON, 0, 1),
            row(TURN_ON, 1, 2),
            row(TURN_ON, 2, 3),
            row(TURN_OFF, 0, 0),
            row(TURN_OFF, 1, 0),
            row(TURN_OFF, 2, 1),
            row(TOGGLE, 0, 2),
            row(TOGGLE, 1, 3),
            row(TOGGLE, 2, 4),
        ) { action, state, result ->
            BrightnessStateChanger.stateAfter(state, action) shouldBe result
        }
    }
})