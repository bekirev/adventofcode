package com.github.bekirev.aoc.utils

fun <R, T : R> Sequence<T>.append(other: Sequence<R>): Sequence<R> = sequence {
    yieldAll(this@append)
    yieldAll(other)
}

fun <R, T : R> Sequence<T>.append(elem: R): Sequence<R> = sequence {
    yieldAll(this@append)
    yield(elem)
}

fun <K, V> Sequence<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = toMutableMap { mutableMapOf() }

fun <K, V> Sequence<Pair<K, V>>.toHashMap(): HashMap<K, V> = toMutableMap { HashMap() }

fun <K, V, M : MutableMap<K, V>> Sequence<Pair<K, V>>.toMutableMap(mapProvider: () -> M): M {
    val map = mapProvider()
    map.putAll(this)
    return map
}