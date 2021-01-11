package com.github.bekirev.aoc.year2015.day09

typealias City = String
typealias Distance = Int

interface Route {
    val from: City
    val to: City
    val distance: Distance
}