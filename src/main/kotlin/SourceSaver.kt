import com.google.gson.Gson
import java.io.File

@Suppress("UNCHECKED_CAST")
class SourceSaver {

    var sourcesList: MutableList<MutableMap<String, String>> = mutableListOf()
    private val saveFile = File(System.getProperty("user.home") + "\\.sourcemanagerK\\sources.json")


    // Add Source to List as Map
    fun addSource(url: String, title: String, authorFirstName: String, authorLastName: String, releaseYear: String, date: String) {
        sourcesList.add(mutableMapOf("url" to url, "title" to title, "authorFirstName" to authorFirstName, "authorLastName" to authorLastName, "releaseYear" to releaseYear, "date" to date))
    }
    // Remove Map from List specified by Index in List
    fun removeSource(index: Int) {
        sourcesList.removeAt(index)
    }
    // Return one Source as String
    fun getFormatedSource(index: Int, flat: Boolean = true): String {

        val currentsource = sourcesList[index]

        if (!flat) {
            val source = """
                Index: $index
                URL: ${currentsource["url"]}
                Title: ${currentsource["title"]}
                Author: ${currentsource["authorFirstName"]} ${currentsource["authorLastName"]}
                Release Year: ${currentsource["releaseYear"]}
                Date of Check: ${currentsource["date"]}""".trimIndent()

            return source

        } else {
            if (currentsource["authorLastName"]?.isEmpty() == false) {
                val source = "[] ${currentsource["authorLastName"]}, ${currentsource["authorFirstName"]} (${currentsource["releaseYear"]}): ${currentsource["title"]} URL: ${currentsource["url"]} [Stand: ${currentsource["date"]}]"
                return source
            } else {
                val source = "[] ${currentsource["authorFirstName"]} (${currentsource["releaseYear"]}): ${currentsource["title"]} URL: ${currentsource["url"]} [Stand: ${currentsource["date"]}]"
                return source
            }
        }
    }
    // Return All Sources as String
    fun listAllSources(flat: Boolean = true): String {
        var toString = ""

        for (index in 0..<sourcesList.size) {
            toString += getFormatedSource(index, flat) + "\n\n"
        }
        return toString
    }

    private fun saveListToFile() {
        val gson = Gson()
        val jsonString = gson.toJson(sourcesList)
        File(saveFile.path).writeText(jsonString)
    }

    private fun loadListFromFile(): MutableList<MutableMap<String, String>>{
        val gson = Gson()
        val jsonString = File(saveFile.path).readText()
        return gson.fromJson(jsonString, MutableList::class.java) as MutableList<MutableMap<String, String>>
    }

    fun saveSources() {
        saveListToFile()
    }

    fun loadSources() {
        sourcesList = loadListFromFile()
    }

    fun createSaveFile() {
        val folderPath = File(System.getProperty("user.home") + "\\.sourcemanagerK\\")

        if (!folderPath.exists()) {
            folderPath.mkdirs()
        }

        if (!saveFile.exists()) {
            saveFile.createNewFile()
        }

        if (isFileEmpty(saveFile.path)) {
            val jsonArray = listOf<Any>()
            val gson = Gson()

            val jsonString = gson.toJson(jsonArray)
            File(saveFile.path).writeText(jsonString)
        }
    }
}