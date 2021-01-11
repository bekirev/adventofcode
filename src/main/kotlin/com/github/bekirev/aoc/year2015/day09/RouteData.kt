package com.github.bekirev.aoc.year2015.day09

data class RouteData(
    override val from: City,
    override val to: City,
    override val distance: Distance,
) : Route