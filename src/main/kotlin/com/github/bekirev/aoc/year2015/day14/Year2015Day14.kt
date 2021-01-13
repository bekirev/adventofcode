package com.github.bekirev.aoc.year2015.day14

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day14)

object Year2015Day14 : Year2015Day(14) {
    private const val raceTime: Time = 2503

    override fun first(input: String): Int {
        return input.lineSequence()
            .filter(String::isNotBlank)
            .map(String::toReindeer)
            .map { it.coveredDistance(raceTime) }
            .maxOrNull()!!
    }
}

typealias Name = String
typealias Speed = Int
typealias Time = Int
typealias Distance = Int

class Reindeer(
    private val name: Name,
    private val speed: Speed,
    private val flyTime: Time,
    private val restTime: Time,
) {
    fun coveredDistance(time: Time): Distance {
        val fullCycleTime = flyTime + restTime
        val fullCyclesCount = time / fullCycleTime
        val lastCycleFlyTime = when (val lastCycleTime = time % fullCycleTime) {
            in 0..flyTime -> lastCycleTime
            else -> flyTime
        }
        val timeToRun = fullCyclesCount * flyTime + lastCycleFlyTime
        return speed * timeToRun
    }
}

fun String.toReindeer(): Reindeer {
    val (name, speed, speedTime, restTime) = split(
        " can fly ",
        " km/s for ",
        " seconds, but then must rest for ",
        " seconds."
    )
    return Reindeer(
        name,
        speed.toInt(),
        speedTime.toInt(),
        restTime.toInt(),
    )
}