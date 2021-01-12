package com.github.bekirev.aoc.year2015.day13

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.utils.append
import com.github.bekirev.aoc.utils.possibleCombinations
import com.github.bekirev.aoc.year2015.Year2015Day
import java.util.stream.Collectors
import kotlin.streams.asStream

fun main() = Day.run(Year2015Day13)

object Year2015Day13 : Year2015Day(13) {
    override fun first(input: String): Int {
        val rules = input.lineSequence()
            .filter(String::isNotBlank)
            .map(String::toHappinessChangeRule)
            .toList()
        return optimalSeatingArrangementTotalChangeInHappiness(rules)
    }

    private const val ME: Person = "ME"

    override fun second(input: String): Int {
        val originalRules = input.lineSequence()
            .filter(String::isNotBlank)
            .map(String::toHappinessChangeRule)
            .toList()
        val originalPeople = people(originalRules.asSequence())
        val rules = originalRules.asSequence().append(
            originalPeople.flatMap { person ->
                sequenceOf(
                    HappinessChangeRule(ME, person, 0),
                    HappinessChangeRule(person, ME, 0),
                )
            }
        ).toList()
        return optimalSeatingArrangementTotalChangeInHappiness(rules)
    }

    private fun optimalSeatingArrangementTotalChangeInHappiness(rules: List<HappinessChangeRule>): Int {
        val happinessChange = happinessChanges(rules.asSequence())
        return people(rules.asSequence()).toSet()
            .possibleCombinations()
            .map { it.totalHappinessChange(happinessChange) }
            .maxOrNull()!!
    }

    private fun List<Person>.totalHappinessChange(happinessChange: Map<Person, Map<Person, Happiness>>): Happiness =
        withNeighbors()
            .flatMap {
                val personsHappinessChangeMap = happinessChange[it.first]!!
                it.second.map { otherPerson ->
                    personsHappinessChangeMap[otherPerson]!!
                }
            }
            .sum()

    private fun <T> List<T>.withNeighbors(): Sequence<Pair<T, Sequence<T>>> {
        fun Int.neighborsIndices(): Sequence<Int> = when (size) {
            1 -> emptySequence()
            2 -> sequenceOf(
                when (this) {
                    0 -> 1
                    1 -> 0
                    else -> throw IllegalArgumentException()
                }
            )
            else -> when (this) {
                0 -> sequenceOf(1, lastIndex)
                lastIndex -> sequenceOf(lastIndex - 1, 0)
                else -> sequenceOf(this - 1, this + 1)
            }
        }
        return asSequence().mapIndexed { index, t ->
            t to index.neighborsIndices().map { get(it) }
        }
    }

    private fun happinessChanges(rules: Sequence<HappinessChangeRule>): Map<Person, Map<Person, Happiness>> =
        rules.asStream()
            .collect(
                Collectors.toMap(
                    { it.person },
                    { HashMap<Person, Happiness>().apply { put(it.otherPerson, it.happinessChange) } },
                    { mapA, mapB ->
                        mapA.apply {
                            mapA.putAll(mapB)
                        }
                    },
                )
            )

    private fun people(rules: Sequence<HappinessChangeRule>): Sequence<Person> =
        rules
            .flatMap {
                sequenceOf(
                    it.person,
                    it.otherPerson,
                )
            }
            .distinct()
}

typealias Happiness = Int
typealias Person = String

fun String.toHappinessChangeRule(): HappinessChangeRule {
    val (person, happinessChangeStr, otherPerson) = split(" would ", " happiness units by sitting next to ", ".")
    val (action, unitsStr) = happinessChangeStr.split(" ")
    val units = unitsStr.toInt()
    val happinessChange = when (action) {
        "gain" -> units
        "lose" -> -units
        else -> throw IllegalArgumentException()
    }
    return HappinessChangeRule(
        person,
        otherPerson,
        happinessChange
    )
}

class HappinessChangeRule(
    val person: Person,
    val otherPerson: Person,
    val happinessChange: Happiness,
)