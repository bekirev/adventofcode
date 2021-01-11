package com.github.bekirev.aoc.year2015.day09

interface RouteRepo {
    fun route(from: City, to: City): Route
}