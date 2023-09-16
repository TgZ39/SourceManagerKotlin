

fun main() {
    println("SourceManager-K 0.1")

    val sources = SourceSaver()
    sources.createSaveFile()
    sources.loadSources()

    while (!wantToExit) {
        selectOption(sources)
    }

}
