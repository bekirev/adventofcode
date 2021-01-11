package com.github.bekirev.aoc.year2015.day09

interface RouteService {
    fun shortestRoute(cities: Collection<City>): Sequence<Route>
    fun longestRoute(cities: Collection<City>): Sequence<Route>
}