

fun main() {
    println("SourceManagerKotlin v1.0.1")

    val sources = SourceSaver()
    sources.createSaveFile()
    sources.loadSources()

    while (!wantToExit) {
        selectOption(sources)
    }

}
