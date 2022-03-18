package miklenda.marek.mepytaruon

class MepytaruonException : Exception {
    constructor() : super()
    constructor(x: Int, y: Int, message: String) : super("[$x, $y]: $message")
    constructor(x: Int, y: Int, message: String, cause: Throwable) : super("[$x, $y]: $message", cause)
    constructor(cause: Throwable) : super(cause)
}