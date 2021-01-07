package com.github.bekirev.aoc.year2015.day07

import com.github.bekirev.aoc.day.Day
import com.github.bekirev.aoc.year2015.Year2015Day

fun main() = Day.run(Year2015Day07)

object Year2015Day07 : Year2015Day(7) {
    override fun first(input: String): Int =
        firstAWireSignal

    override fun second(input: String): Int =
        signalAtAWire(wireToDesc + ("b" to BridgeSignalSourceDescription(ConstantOperand(firstAWireSignal))))

    private fun signalAtAWire(wireToDesc: Map<WireId, SignalSourceDescription>): Int {
        return buildSignalSourceFor(
            "a",
            wireToDesc
        ).signal.intValue
    }

    private val firstAWireSignal by lazy {
        signalAtAWire(wireToDesc)
    }

    private val wireToDesc by lazy {
        input
            .lineSequence()
            .filter(String::isNotBlank)
            .map(String::toCircuitElementDescription)
            .map { it.outputWireIdentifier to it.signalSourceDescription }
            .toMap()
    }
}

fun buildSignalSourceFor(
    wire: WireId,
    wireToInputMap: Map<WireId, SignalSourceDescription>,
): SignalSource {
    val wireMap = mutableMapOf<WireId, Lazy<SignalSource>>()
    fun Operand.toSignalSource(): SignalSource = when (this) {
        is WireOperand -> wireMap[wireId]!!.value
        is ConstantOperand -> ConstantSignal(IntSignal(value))
    }
    wireToInputMap.forEach { (wireId, desc) ->
        wireMap[wireId] = lazy {
            when (desc) {
                is BridgeSignalSourceDescription -> desc.operand.toSignalSource()
                is AndSignalSourceDescription -> AndGate(
                    desc.leftOperand.toSignalSource(),
                    desc.rightOperand.toSignalSource(),
                )
                is OrSignalSourceDescription -> OrGate(
                    desc.leftOperand.toSignalSource(),
                    desc.rightOperand.toSignalSource(),
                )
                is NotSignalSourceDescription -> NotGate(
                    desc.operand.toSignalSource(),
                )
                is LeftShiftSourceDescription -> LeftShiftGate(
                    desc.operand.toSignalSource(),
                    desc.bitCount,
                )
                is RightShiftSourceDescription -> RightShiftGate(
                    desc.operand.toSignalSource(),
                    desc.bitCount,
                )
            }
        }
    }
    return wireMap[wire]!!.value
}

typealias WireId = String

class CircuitElementDescription(
    val signalSourceDescription: SignalSourceDescription,
    val outputWireIdentifier: WireId,
)

sealed class SignalSourceDescription

class BridgeSignalSourceDescription(
    val operand: Operand,
) : SignalSourceDescription()

sealed class BinaryWireSignalSourceDescription(
    val leftOperand: Operand,
    val rightOperand: Operand,
) : SignalSourceDescription()

class AndSignalSourceDescription(
    leftOperand: Operand,
    rightOperand: Operand,
) : BinaryWireSignalSourceDescription(leftOperand, rightOperand)

class OrSignalSourceDescription(
    leftOperand: Operand,
    rightWireId: Operand,
) : BinaryWireSignalSourceDescription(leftOperand, rightWireId)

class NotSignalSourceDescription(
    val operand: Operand,
) : SignalSourceDescription()

sealed class ShiftSourceDescription(
    val operand: Operand,
    val bitCount: Int,
) : SignalSourceDescription()

class LeftShiftSourceDescription(
    operand: Operand,
    bitCount: Int,
) : ShiftSourceDescription(operand, bitCount)

class RightShiftSourceDescription(
    operand: Operand,
    bitCount: Int,
) : ShiftSourceDescription(operand, bitCount)

fun String.toCircuitElementDescription(): CircuitElementDescription {
    val (signalSourceDescriptionStr, wireIdentifierStr) = split(" -> ")
    return CircuitElementDescription(
        signalSourceDescriptionStr.toSignalSourceDescription(),
        wireIdentifierStr
    )
}

sealed class Operand
class WireOperand(val wireId: WireId) : Operand()
class ConstantOperand(val value: Int) : Operand()

private val NUMBER_REGEX = Regex("""\d+""")
fun String.toOperand(): Operand =
    if (matches(NUMBER_REGEX)) ConstantOperand(toInt())
    else WireOperand(this)

fun String.toSignalSourceDescription(): SignalSourceDescription = when {
    contains("AND") -> {
        val (leftOperand, rightOperand) = split(" AND ")
        AndSignalSourceDescription(leftOperand.toOperand(), rightOperand.toOperand())
    }
    contains("OR") -> {
        val (leftOperand, rightOperand) = split(" OR ")
        OrSignalSourceDescription(leftOperand.toOperand(), rightOperand.toOperand())
    }
    contains("NOT") -> NotSignalSourceDescription(substringAfter("NOT ").toOperand())
    contains("LSHIFT") -> {
        val (operand, bitCount) = split(" LSHIFT ")
        LeftShiftSourceDescription(operand.toOperand(), bitCount.toInt())
    }
    contains("RSHIFT") -> {
        val (operand, bitCount) = split(" RSHIFT ")
        RightShiftSourceDescription(operand.toOperand(), bitCount.toInt())
    }
    else -> BridgeSignalSourceDescription(toOperand())
}