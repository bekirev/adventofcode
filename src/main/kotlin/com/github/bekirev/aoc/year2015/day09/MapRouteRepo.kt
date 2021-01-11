package com.github.bekirev.aoc.year2015.day09

import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.streams.asStream

class MapRouteRepo private constructor(
    private val routes: Map<City, Map<City, Distance>>,
) : RouteRepo {
    constructor(routes: Sequence<Route>) : this(
        routes
            .asStream()
            .flatMap { route ->
                Stream.of(
                    route.from to (route.to to route.distance),
                    route.to to (route.from to route.distance),
                )
            }
            .collect(
                Collectors.toMap(
                    { it.first },
                    {
                        HashMap<City, Distance>().apply {
                            put(it.second.first, it.second.second)
                        }
                    },
                    { mapA, mapB ->
                        mapA.apply {
                            putAll(mapB)
                        }
                    }
                )
            )
    )

    override fun route(from: City, to: City) = routes.get(from)?.get(to)?.let { distance ->
        RouteData(
            from,
            to,
            distance
        )
    } ?: throw IllegalArgumentException()
}