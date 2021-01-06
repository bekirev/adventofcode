package com.github.bekirev.aoc.day

import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

abstract class Day(val year: Int, val day: Int) {
    val input: String by lazy { runBlocking { Fetcher.fetchInput(year, day) } }
    open fun first(input: String): String? = null
    open fun second(input: String): String? = null

    companion object {
        fun run(aDay: Day) {
            with(aDay) {
                println("Year $year, day $day")
                measureTimeMillis {
                    println("First: ${first(input) ?: "unsolved"}")
                }.run {
                    println("Time: ${this}ms")
                }
                measureTimeMillis {
                    println("Second: ${second(input) ?: "unsolved"}")
                }.run {
                    println("Time: ${this}ms")
                }
            }
        }
    }
}