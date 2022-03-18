package miklenda.marek.mepytaruon.data

import kotlin.math.abs
import kotlin.math.sqrt

internal class Pixel(val r: Int, val g: Int, val b: Int) {
    constructor(base: Int) : this((base shr 16).toUByte().toInt(), (base shr 8).toUByte().toInt(), (base).toUByte().toInt())

    val magnitude = sqrt((r * r + g * g + b * b).toDouble())

    override fun toString(): String = "#${
        Integer.toHexString(r).uppercase().padStart(2, '0')
    }${
        Integer.toHexString(g).uppercase().padStart(2, '0')
    }${
        Integer.toHexString(b).uppercase().padStart(2, '0')
    }"

    fun isSameAsBase(other: Pixel): Boolean = doubEq(r / magnitude, other.r / other.magnitude) &&
            doubEq(g / magnitude, other.g / other.magnitude) &&
            doubEq(b / magnitude, other.b / other.magnitude)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pixel

        if (r != other.r) return false
        if (g != other.g) return false
        if (b != other.b) return false

        return true
    }

    override fun hashCode(): Int {
        var result = r
        result = 31 * result + g
        result = 31 * result + b
        return result
    }

    companion object {
        private fun doubEq(a: Double, b: Double): Boolean = abs(a - b) < 0.1
    }
}