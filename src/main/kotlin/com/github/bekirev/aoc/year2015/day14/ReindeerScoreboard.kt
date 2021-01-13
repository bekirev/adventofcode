package com.github.bekirev.aoc.year2015.day14

class ReindeerScoreboard private constructor(
    private val scoreByReindeer: MutableMap<Name, Score>,
) {
    constructor() : this(mutableMapOf())

    fun addPoint(reindeer: Sequence<Name>) {
        reindeer.forEach { name ->
            scoreByReindeer.merge(name, 1, Score::plus)
        }
    }

    fun winner(): Pair<Name, Score> {
        val winnerEntry = scoreByReindeer.entries.maxByOrNull { it.value }!!
        return winnerEntry.key to winnerEntry.value
    }
}