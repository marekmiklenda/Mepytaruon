package miklenda.marek.mepytaruon.data

import miklenda.marek.mepytaruon.control.Environment

internal enum class TileType(source: Int, val numArgs: Byte, val action: (Environment, Int) -> Unit) {
    PINK(0xFFC0C0, 0, { _, _ -> }),     // NOP
    GREEN(0x9CFF79, 4, { e, a ->        // Manipulates pointer (nothing, reset, inc, dec)
        when (a) {
            1 -> e.memoryPointer = 0
            2 -> e.memoryPointer++
            3 -> e.memoryPointer--
        }
    }),
    RED(0xFF4040, 0, { e, _ ->          // Wall, cannot be entered, colliding with one terminates the program
        e.returned = true
    }),
    YELLOW(0xFFFF80, 9, { e, a ->       // Sends the protagonist back on the tile they've been on previously, triggering both. Value specifies new movement direction (constant, relative, pointer)
        setDirectionFromArg(a, e)
    }),
    ORANGE(0xFFC14A, 4, { e, a ->       // Changes flavor to orange if value at pointer is 0, outputs value at pointer (no output, int, hex, char)
        if (e.memoryCells[e.memoryPointer] == 0.toShort()) e.flavor = Flavor.ORANGE

        when (a) {
            1 -> e.stdout(e.memoryCells[e.memoryPointer].toString())
            2 -> e.stdout("0x${Integer.toHexString(e.memoryCells[e.memoryPointer].toInt())}")
            3 -> e.stdout(Char(e.memoryCells[e.memoryPointer].toInt()).toString())
        }

        if (e.trace && a != 0) {
            e.stdout("[TRACE] Press enter to continue...")
            e.stdin()
        }
    }),
    PURPLE(0xC000C0, 5, { e, a ->       // Ice tile, changes flavor to lemon, manipulates value at pointer (nothing, reset, inc, dec, input)
        e.flavor = Flavor.LEMON

        when (a) {
            1 -> e.memoryCells[e.memoryPointer] = 0
            2 -> e.memoryCells[e.memoryPointer]++
            3 -> e.memoryCells[e.memoryPointer]--
            4 -> e.memoryCells[e.memoryPointer] = e.stdin()
        }
    }),
    BLUE(0x4040FF, 9, { e, a ->         // case next to yellow: acts like yellow; case orange: act like yellow, and apply rotation (yellow) break; else act like pink (NOP)
        if (e.flavor == Flavor.ORANGE) setDirectionFromArg(a, e)
    }),
    WHITE(0xFFFFFF, 0, { e, _ ->        // debug. dumps cells and wait for newline
        e.stdout("${e.memoryCells.joinToString(separator = " ") { x -> "[$x]" }}\nCell pointer: ${e.memoryPointer}\n")
        e.stdout("[TRACE] Press enter to continue...")
        e.stdin()
    });

    val base = Pixel(source)

    companion object {
        fun fromPixel(pixel: Pixel): TileType? = values().firstOrNull { pixel.isSameAsBase(it.base) }

        fun createColorMap(tile: TileType): String {
            val sb = StringBuilder()
            sb.appendLine("0: ${tile.base}")

            for (i in 0 until tile.numArgs - 1) {
                val reqMag = tile.base.magnitude * (tile.numArgs - i) / tile.numArgs - 0.5 * tile.base.magnitude / tile.numArgs
                val r = (tile.base.r / tile.base.magnitude * reqMag).toInt()
                val g = (tile.base.g / tile.base.magnitude * reqMag).toInt()
                val b = (tile.base.b / tile.base.magnitude * reqMag).toInt()
                sb.appendLine("${i + 1}: ${Pixel(r, g, b)}")
            }

            return sb.toString()
        }

        private fun setDirectionFromArg(a: Int, e: Environment) {
            when (a) {
                in 0..3 -> e.ipDirection = IPDirection.values()[a]
                in 4..7 -> e.ipDirection = e.ipDirection.rotateBy(IPDirection.values()[a - 4])
                8 -> e.ipDirection = IPDirection.values()[e.memoryCells[e.memoryPointer].toInt().rem(4)]
            }
        }
    }
}