package com.github.bekirev.aoc.year2015.day09

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day09)

object Year2015Day09 : Year2015Day(9) {
    override fun first(input: String): Int {
        val routes = routes(input)
        val routeRepo = routeRepo(routes.asSequence())
        val routeService = routeService(routeRepo)
        val cities = cities(routes.asSequence())
        return routeService.shortestRoute(cities)
            .sumBy(Route::distance)
    }

    override fun second(input: String): Int {
        val routes = routes(input)
        val routeRepo = routeRepo(routes.asSequence())
        val routeService = routeService(routeRepo)
        val cities = cities(routes.asSequence())
        return routeService.longestRoute(cities)
            .sumBy(Route::distance)
    }

    private fun routes(input: String): List<Route> =
        input.lineSequence()
            .filter(String::isNotBlank)
            .map(String::toRoute)
            .toList()

    private fun cities(routes: Sequence<Route>): Collection<City> =
        routes.asSequence()
            .flatMap {
                sequenceOf(it.from, it.to)
            }
            .distinct()
            .toSet()

    private fun routeService(routeRepo: RouteRepo): RouteService =
        RouteServiceImpl(routeRepo)

    private fun routeRepo(routes: Sequence<Route>): RouteRepo =
        MapRouteRepo(routes)
}

fun String.toRoute(): Route {
    val (from, to, distance) = split(" to ", " = ")
    return RouteData(
        from,
        to,
        distance.toInt()
    )
}
