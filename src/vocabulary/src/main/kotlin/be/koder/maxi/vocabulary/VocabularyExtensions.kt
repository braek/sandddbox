package be.koder.maxi.vocabulary

fun String.sanitizeSingleLineString(): String {
    return this.trim()
        .replace("\n", "")
        .replace("\t", "")
        .replace("\r", "")
}

fun String.sanitizeMultiLineString(): String {
    return this.trim()
        .replace("\r", "")
        .replace("\t", "")
        .replace("\n{2,}".toRegex(), "\n")
        .replace("\n".toRegex(), "\r\n")
}