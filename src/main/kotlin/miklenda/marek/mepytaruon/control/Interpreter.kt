package miklenda.marek.mepytaruon.control

import miklenda.marek.mepytaruon.MepytaruonException
import java.awt.image.BufferedImage

object Interpreter {
    fun run(image: BufferedImage, environment: Environment = Environment()) = run(ProgramReader(image, environment), environment)
    fun run(path: String, environment: Environment = Environment()) = run(ProgramReader(path, environment), environment)
    private fun run(pr: ProgramReader, env: Environment = Environment()): Int {
        try {
            while (!env.returned) pr.step()
        } catch (e: MepytaruonException) {
            env.stdout(e.message ?: "An unknown error has occurred.")
            env.returned = true
            return 1
        }

        return 0
    }
}