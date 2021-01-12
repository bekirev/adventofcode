package com.github.bekirev.aoc.year2015.day09

import com.github.bekirev.aoc.utils.possibleCombinations

class RouteServiceImpl(
    private val routeRepo: RouteRepo,
) : RouteService {
    override fun shortestRoute(cities: Collection<City>): Sequence<Route> =
        selectRoute(cities) {
            minByOrNull { it.sumBy(Route::distance) }
        }

    override fun longestRoute(cities: Collection<City>): Sequence<Route> =
        selectRoute(cities) {
            maxByOrNull { it.sumBy(Route::distance) }
        }

    private fun selectRoute(
        cities: Collection<City>,
        selector: Sequence<List<Route>>.() -> List<Route>?,
    ): Sequence<Route> =
        routes(cities).selector()?.asSequence() ?: throw IllegalArgumentException()

    private fun routes(cities: Collection<City>) =
        cities.possibleCombinations()
            .map {
                it.asSequence().zipWithNext().map { (from, to) ->
                    routeRepo.route(from, to)
                }.toList()
            }
}