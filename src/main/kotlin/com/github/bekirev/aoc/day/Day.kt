package com.github.bekirev.aoc.day

import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

abstract class Day(val year: Int, val day: Int) {
    val input: String = runBlocking { Fetcher.fetchInput(year, day) }
    open fun first(input: String): String? = null
    open fun second(input: String): String? = null

    companion object {
        fun <I, O> run(aDay: Day) {
            with(aDay) {
                println("Year $year, day $day")
                measureTimeMillis {
                    print("First: ${first(input) ?: "unsolved"}")
                }.run {
                    println("Time: ${this}ms")
                }
                measureTimeMillis {
                    print("Second: ${second(input) ?: "unsolved"}")
                }.run {
                    println("Time: ${this}ms")
                }
            }
        }
    }
}