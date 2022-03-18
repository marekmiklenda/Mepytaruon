package miklenda.marek.mepytaruon.control

import miklenda.marek.mepytaruon.MepytaruonException
import miklenda.marek.mepytaruon.data.Flavor
import miklenda.marek.mepytaruon.data.IPDirection
import miklenda.marek.mepytaruon.data.Pixel
import miklenda.marek.mepytaruon.data.TileType
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

internal class ProgramReader(private val image: BufferedImage, private val environment: Environment) {
    constructor(path: String, environment: Environment) : this(ImageIO.read(File(path)), environment)

    fun step() {
        moveBy(environment.ipDirection)
    }

    private fun moveBy(direction: IPDirection) {
        var ret: Pair<TileType, IPDirection> = actuallyMoveBy(direction)
        while (true) {
            val lastDirection = ret.second

            ret = when (ret.first) {
                TileType.BLUE -> if (neighboursWith(environment.ipX, environment.ipY, TileType.YELLOW) || environment.flavor == Flavor.ORANGE)
                    actuallyMoveBy(lastDirection.rotateBy(IPDirection.REVERSE)) else break
                TileType.YELLOW -> actuallyMoveBy(lastDirection.rotateBy(IPDirection.REVERSE))
                TileType.PURPLE -> actuallyMoveBy(lastDirection)
                else -> break
            }
        }
    }

    private fun actuallyMoveBy(direction: IPDirection): Pair<TileType, IPDirection> {
        environment.ipX += direction.absoluteXChange
        environment.ipY += direction.absoluteYChange

        //FIXME: Maybe terminate instead of throwing
        if (environment.ipX !in 0 until image.width || environment.ipY !in 0 until image.height) throw MepytaruonException(environment.ipX, environment.ipY, "Attempting to move out of bounds: [${environment.ipX}, ${environment.ipY}]!")

        val rawTile = Pixel(image.getRGB(environment.ipX, environment.ipY))
        val tile = TileType.fromPixel(rawTile) ?: throw MepytaruonException(environment.ipX, environment.ipY, "Invalid Mepytaruon pixel: $rawTile!")

        val arg = tile.numArgs - tile.numArgs * rawTile.magnitude.toInt() / tile.base.magnitude.toInt()

        if (environment.trace) environment.stdout("[${environment.ipX}, ${environment.ipY}]: $tile\n")
        tile.action(environment, arg)

        return Pair(tile, direction)
    }

    private fun neighboursWith(posX: Int, posY: Int, tile: TileType): Boolean {
        IPDirection.values().forEach next@{
            val newX = posX + it.absoluteXChange
            val newY = posY + it.absoluteYChange
            if (newX !in 0 until image.width || newY !in 0 until image.height) return@next

            if (TileType.fromPixel(Pixel(image.getRGB(newX, newY))) == tile) return true
        }

        return false
    }
}