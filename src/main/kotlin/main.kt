import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import miklenda.marek.mepytaruon.control.Environment
import miklenda.marek.mepytaruon.control.Interpreter

internal fun main(args: Array<String>) = mainBody {
    ArgParser(args).parseInto(::MyArgs).run {
        val exit = Interpreter.run(program, Environment(x, y, direction, cells, trace))
        println("\nProcess finished with exit code $exit")
    }
}