package com.github.bekirev.aoc.year2021.day02

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2021.Year2021Day

fun main() = Day.run(Year2021Day02)

object Year2021Day02 : Year2021Day(2) {
    override fun first(input: String): Int =
        FirstSubmarinePositionPredictor.predict(
            submarine = FirstSubmarine(),
            commands = input.commands(),
        ).toResult()

    override fun second(input: String): Int =
        SecondSubmarinePositionPredictor.predict(
            submarine = SecondSubmarine(),
            commands = input.commands(),
        ).toResult()

    private fun String.commands() =
        lineSequence()
            .mapNotNull { it.commandOrNull() }

    private fun SubmarinePosition.toResult(): Int =
        forward * depth

    interface SubmarinePositionPredictor<S> {
        fun predict(submarine: S, commands: Sequence<SubmarineCommand>): SubmarinePosition
    }

    data class SubmarinePosition(
        val depth: Int = 0,
        val forward: Int = 0,
    )

    sealed interface SubmarineCommand {
        data class Forward(val value: Int) : SubmarineCommand
        data class Down(val value: Int) : SubmarineCommand
        data class Up(val value: Int) : SubmarineCommand
    }

    private fun String.commandOrNull(): SubmarineCommand? = when {
        startsWith(forwardCommandPrefix) -> SubmarineCommand.Forward(
            removePrefix(forwardCommandPrefix).toInt()
        )
        startsWith(downCommandPrefix) -> SubmarineCommand.Down(
            removePrefix(downCommandPrefix).toInt()
        )
        startsWith(upCommandPrefix) -> SubmarineCommand.Up(
            removePrefix(upCommandPrefix).toInt()
        )
        isBlank() -> null
        else -> error("Unknown command: $this")
    }

    data class FirstSubmarine(
        val pos: SubmarinePosition = SubmarinePosition(),
    )

    object FirstSubmarinePositionPredictor : SubmarinePositionPredictor<FirstSubmarine> {
        override fun predict(submarine: FirstSubmarine, commands: Sequence<SubmarineCommand>): SubmarinePosition =
            commands.fold(submarine) { sub, command ->
                sub.apply(command)
            }.pos

        private fun FirstSubmarine.apply(command: SubmarineCommand): FirstSubmarine = when (command) {
            is SubmarineCommand.Forward -> copy(pos = pos.copy(forward = pos.forward + command.value))
            is SubmarineCommand.Down -> copy(pos = pos.copy(depth = pos.depth + command.value))
            is SubmarineCommand.Up -> copy(pos = pos.copy(depth = pos.depth - command.value))
        }
    }

    data class SecondSubmarine(
        val pos: SubmarinePosition = SubmarinePosition(),
        val aim: Int = 0,
    )

    object SecondSubmarinePositionPredictor : SubmarinePositionPredictor<SecondSubmarine> {
        override fun predict(submarine: SecondSubmarine, commands: Sequence<SubmarineCommand>): SubmarinePosition =
            commands.fold(submarine) { sub, command ->
                sub.apply(command)
            }.pos

        private fun SecondSubmarine.apply(command: SubmarineCommand): SecondSubmarine = when (command) {
            is SubmarineCommand.Forward -> copy(
                pos = pos.copy(
                    forward = pos.forward + command.value,
                    depth = pos.depth + aim * command.value
                )
            )
            is SubmarineCommand.Down -> copy(aim = aim + command.value)
            is SubmarineCommand.Up -> copy(aim = aim - command.value)
        }
    }

    private const val forwardCommandPrefix = "forward "
    private const val downCommandPrefix = "down "
    private const val upCommandPrefix = "up "
}
