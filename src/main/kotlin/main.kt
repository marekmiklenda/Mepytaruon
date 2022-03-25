import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import miklenda.marek.mepytaruon.control.Environment
import miklenda.marek.mepytaruon.control.Interpreter
import java.io.File
import javax.imageio.IIOException
import kotlin.system.exitProcess

internal fun main(args: Array<String>) = mainBody {
    ArgParser(args).parseInto(::MyArgs).run {
        val file = File(program)
        if (!file.exists()) {
            System.err.println("The file specified doesn't exist!")
            return@mainBody
        }

        val type = file.toURL().openConnection().contentType
        if (type == "image/jpeg") System.err.println("Warning: image/jpeg files may not be executed correctly. Consider using image/png instead.\n")

        try {
            val exit = Interpreter.run(program, Environment(x, y, direction, cells, trace))
            exitProcess(exit)
        } catch (e: Exception) {
            when (e) {
                is NullPointerException,
                is IIOException -> System.err.println("Invalid file type: $type") //${Regex("\\..+$").find(program)?.value}
                else -> System.err.println("An unknown error has occurred.\n${e.stackTraceToString()}")
            }
        }
    }
}