package com.pipeytube.data.newpipe

sealed class NewPipeError(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
    class Network(cause: Throwable) : NewPipeError("Network failure", cause)
    class Parsing(cause: Throwable) : NewPipeError("Extractor payload parsing failure", cause)
    class Extractor(cause: Throwable) : NewPipeError("Extractor failure", cause)
    class Unknown(cause: Throwable) : NewPipeError("Unknown repository failure", cause)
}
