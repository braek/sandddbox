package be.koder.maxi.vocabulary

fun String.sanitizeSingleLineString(): String {
    return this.replace("\r", "")
        .replace("\t", "")
        .replace("\n", "")
        .trim()
}

fun String.sanitizeMultiLineString(): String {
    return this.replace("\r", "")
        .replace("\t", "")
        .replace("\n{2,}".toRegex(), "\n")
        .replace("\n".toRegex(), "\r\n")
        .trim()
}