package miklenda.marek.mepytaruon.control

import miklenda.marek.mepytaruon.MepytaruonException
import miklenda.marek.mepytaruon.data.Flavor
import miklenda.marek.mepytaruon.data.IPDirection

class Environment(
    internal var ipX: Int = 0,
    internal var ipY: Int = 1,
    internal var ipDirection: IPDirection = IPDirection.RIGHT,
    numberOfCells: Int = 30000,
    internal var trace: Boolean = false,
    internal val stdin: () -> String = { readLine() ?: throw MepytaruonException(ipX, ipY, "Invalid input!") },
    internal val stdout: (String) -> Unit = { print(it) }
) {
    init {
        ipX -= ipDirection.absoluteXChange
        ipY -= ipDirection.absoluteYChange
    }

    var returned = false
        internal set
    internal val memoryCells = IntArray(numberOfCells)
    internal var memoryPointer: Int = 0
        set(value) {
            if (value !in memoryCells.indices)
                throw MepytaruonException(ipX, ipY, "Pointer index out of bounds. Attempting to set memory pointer to: $value, with ${memoryCells.size} memory cells present.")
            field = value
        }
    internal var flavor = Flavor.LEMON
}