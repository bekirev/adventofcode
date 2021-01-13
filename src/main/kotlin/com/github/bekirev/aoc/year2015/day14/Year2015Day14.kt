package com.github.bekirev.aoc.year2015.day14

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day14)

object Year2015Day14 : Year2015Day(14) {
    private const val raceTime: Time = 2503

    override fun first(input: String): Int {
        return reindeer(input)
            .map { it.coveredDistance(raceTime) }
            .maxOrNull()!!
    }

    override fun second(input: String): Int {
        val reindeer = reindeer(input).toSet()
        val scoreboard = ReindeerScoreboard()
        repeat(raceTime) {
            reindeer.forEach(Reindeer::move)
            val maxPosition = reindeer.asSequence().map(Reindeer::position).maxOrNull()!!
            scoreboard.addPoint(reindeer.asSequence().filter { it.position == maxPosition }.map(Reindeer::name))
        }
        return scoreboard.winner().second
    }

    private fun reindeer(input: String) =
        input.lineSequence()
            .filter(String::isNotBlank)
            .map(String::toReindeer)
}

typealias Name = String
typealias Speed = Int
typealias Time = Int
typealias Distance = Int
typealias Score = Int

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