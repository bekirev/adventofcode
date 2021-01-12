package com.github.bekirev.aoc.utils

fun <R, T : R> Sequence<T>.append(other: Sequence<R>): Sequence<R> = sequence {
    yieldAll(this@append)
    yieldAll(other)
}

fun <R, T : R> Sequence<T>.append(elem: R): Sequence<R> = sequence {
    yieldAll(this@append)
    yield(elem)
}