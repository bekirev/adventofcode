package com.github.bekirev.aoc.day

import com.github.bekirev.aoc.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.cookies.ConstantCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.request.get
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding


object Fetcher {
    private val client = HttpClient(CIO) {
        install(HttpCookies) {
            storage = ConstantCookiesStorage(
                Cookie(
                    name = "session",
                    value = BuildConfig.COOKIE,
                    domain = ".adventofcode.com",
                    encoding = CookieEncoding.RAW,
                )
            )
        }
    }

    suspend fun fetchInput(year: Int, day: Int): String =
        client.get("https://adventofcode.com/$year/day/$day/input")
}