import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import miklenda.marek.mepytaruon.data.IPDirection

internal class MyArgs(parser: ArgParser) {
    val x by parser.storing("starting x coordinate") { toInt() }.default(0)
    val y by parser.storing("starting y coordinate") { toInt() }.default(1)
    val direction by parser.mapping(
        "--start-up" to IPDirection.IDENTITY,
        "--start-right" to IPDirection.RIGHT,
        "--start-down" to IPDirection.REVERSE,
        "--start-left" to IPDirection.LEFT,
        help = "starting walking direction"
    ).default(IPDirection.RIGHT)

    val cells by parser.storing("-c", "--cells", help = "number of memory cells") { toInt() }.default(30000)
    val trace by parser.flagging("-t", "--trace", help = "enable debug mode")

    val program by parser.positional("file to execute")
}