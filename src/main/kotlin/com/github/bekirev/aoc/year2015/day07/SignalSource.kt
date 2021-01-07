package com.github.bekirev.aoc.year2015.day07

interface Signal {
    val intValue: Int
}

private const val SIGNAL_MAX_VALUE = 0xffff

class IntSignal(
    override val intValue: Int,
) : Signal {
    init {
        check(intValue in 0..65535)
    }
}

interface SignalSource {
    val signal: Signal
}

class ConstantSignal(
    override val signal: Signal,
) : SignalSource

class Wire(
    private val signalSource: SignalSource,
) : SignalSource {
    override val signal: Signal by lazy {
        signalSource.signal
    }
}

class AndGate(
    private val leftSignalSource: SignalSource,
    private val rightSignalSource: SignalSource,
) : SignalSource {
    override val signal: Signal by lazy {
        IntSignal(leftSignalSource.signal.intValue and rightSignalSource.signal.intValue)
    }
}

class OrGate(
    private val leftSignalSource: SignalSource,
    private val rightSignalSource: SignalSource,
) : SignalSource {
    override val signal: Signal by lazy {
        IntSignal(leftSignalSource.signal.intValue or rightSignalSource.signal.intValue)
    }
}

class LeftShiftGate(
    private val signalSource: SignalSource,
    private val bitCount: Int,
) : SignalSource {
    override val signal: Signal by lazy {
        IntSignal(signalSource.signal.intValue shl bitCount and SIGNAL_MAX_VALUE)
    }
}

class RightShiftGate(
    private val signalSource: SignalSource,
    private val bitCount: Int,
) : SignalSource {
    override val signal: Signal by lazy {
        IntSignal(signalSource.signal.intValue ushr bitCount)
    }
}

class NotGate(
    private val signalSource: SignalSource,
) : SignalSource {
    override val signal: Signal by lazy {
        IntSignal(signalSource.signal.intValue.inv() and SIGNAL_MAX_VALUE)
    }
}