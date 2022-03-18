package miklenda.marek.mepytaruon.data

enum class IPDirection(private val rotationFactor: Byte, internal val absoluteXChange: Byte, internal val absoluteYChange: Byte) {
    // Warning, order-sensitive!
    IDENTITY(0, 0, -1),    // Identity - forward/up
    RIGHT(1, 1, 0),       // Right
    REVERSE(2, 0, 1),     // Opposite of identity - backwards/back
    LEFT(3, -1, 0);        // Left

    internal fun rotateBy(other: IPDirection): IPDirection = values()[(ordinal + other.rotationFactor) % 4]
}