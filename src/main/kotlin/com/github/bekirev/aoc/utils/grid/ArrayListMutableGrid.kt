package com.github.bekirev.aoc.utils.grid

import com.github.bekirev.aoc.utils.Position2D

class ArrayListMutableGrid<T>(
    override val size: Size,
    private val rows: List<MutableList<T>>,
) : MutableGrid<T> {
    constructor(size: Size, initBlock: (Position2D) -> T) : this(
        size,
        (0 until size.rowCount)
            .map { row ->
                val rowList = ArrayList<T>(size.colCount)
                (0 until size.colCount).forEach { col ->
                    rowList.add(initBlock(Position2D(row, col)))
                }
                rowList
            }
            .toList()
    )

    override fun get(position: Position2D): T =
        rows[position.x][position.y]

    override fun get(row: Int): (Int) -> T {
        val rowList = rows[row]
        return { y -> rowList[y] }
    }

    override fun getAll(): Sequence<PositionValue<T>> =
        size.positions().map { PositionValue(it, get(it)) }

    override fun set(position: Position2D, value: T) {
        rows[position.x][position.y] = value
    }

    override fun set(row: Int): (Int, T) -> Unit {
        val rowList = rows[row]
        return { col, value -> rowList[col] = value }
    }
}