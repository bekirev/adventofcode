package com.github.bekirev.aoc.year2015.day09

class RouteServiceImpl(
    private val routeRepo: RouteRepo,
) : RouteService {
    override fun shortestRoute(cities: Collection<City>): Sequence<Route> =
        possibleCitySequences(cities)
            .map {
                it.asSequence().zipWithNext().map { (from, to) ->
                    routeRepo.route(from, to)
                }.toList()
            }
            .minByOrNull { it.sumBy(Route::distance) }
            ?.asSequence() ?: throw IllegalArgumentException()

    private fun possibleCitySequences(cities: Collection<City>): Sequence<List<City>> {
        fun possibleCitySequences(start: List<City>, citiesToVisit: Collection<City>): Sequence<List<City>> = when {
            citiesToVisit.isEmpty() -> sequenceOf(start)
            else -> citiesToVisit.asSequence().flatMap { nextCity ->
                possibleCitySequences(start + nextCity, citiesToVisit - nextCity)
            }
        }
        return possibleCitySequences(emptyList(), cities)
    }
}