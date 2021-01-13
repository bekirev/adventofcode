package com.github.bekirev.aoc.year2015.day14

class Reindeer(
    val name: Name,
    private val speed: Speed,
    private val flyTime: Time,
    private val restTime: Time,
) {
    private val fullCycleTime by lazy { flyTime + restTime }
    private var state = STATE.FLYING
    private var stateTime: Time = 0
    var position: Int = 0

    private enum class STATE {
        FLYING,
        RESTING,
        ;
    }

    fun coveredDistance(time: Time): Distance {
        val fullCyclesCount = time / fullCycleTime
        val lastCycleFlyTime = when (val lastCycleTime = time % fullCycleTime) {
            in 0..flyTime -> lastCycleTime
            else -> flyTime
        }
        val timeToRun = fullCyclesCount * flyTime + lastCycleFlyTime
        return speed * timeToRun
    }

    fun move() {
        when (state) {
            STATE.FLYING -> when (stateTime) {
                flyTime -> {
                    state = STATE.RESTING
                    stateTime = 1
                }
                else -> ++stateTime
            }
            STATE.RESTING -> when (stateTime) {
                restTime -> {
                    state = STATE.FLYING
                    stateTime = 1
                }
                else -> ++stateTime
            }
        }
        if (state == STATE.FLYING)
            position += speed
    }
}