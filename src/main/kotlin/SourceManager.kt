import com.google.gson.Gson
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

fun main() {
    println("SourceManager-K 0.1")

    val sources = SourceSaver()
    sources.createSaveFile()
    sources.loadSources()

    while (true) {

        print("Choose an option (exit, addsource, deletesource, deleteallsources, listsource, listallsources, save): ")
        val option = readln().lowercase()

        if (option == "exit") {
            sources.saveSources()
            break

        } else if (option == "addsource") {
            println("Add Source")

            print("URL: ")
            var readUrl = readln()

            print("Title: ")
            var readTitle = readln()

            print("Author Firstname: ")
            var readAuthorFirstName = readln()

            print("Author Lastname (leave empty in unknown): ")
            var readAuthorLastName = readln()

            print("Release Year (leave empty for current year): ")
            var readReleaseYear = readln()
            if (readReleaseYear.isEmpty()) {
                readReleaseYear = Calendar.getInstance().get(Calendar.YEAR).toString()
            }

            print("Date of Check (leave empty for current date): ")
            var readDate = readln()
            if (readDate.isEmpty()) {
                readDate = currentDate()
            }

            sources.addSource(readUrl, readTitle, readAuthorFirstName, readAuthorLastName, readReleaseYear, readDate)
            println("Source has been added. (Index: ${sources.sourcesList.size-1}) \n")

        } else if (option == "removesource") {

            if (sources.sourcesList.size == 0) {
                println("You need to add a Source first. \n")
            }

            println("Remove Source")
            print("Index (Number from 0 to ${sources.sourcesList.size-1}): ")
            try {
                var readIndex = readln().toInt()
                sources.removeSource(readIndex)
                println("Source $readIndex has been removed. \n")
            } catch (e: Exception) {
                println("Please enter a valid Number.\n")
            }

        } else if (option == "listsource") {

            if (sources.sourcesList.size == 0) {
                println("You need to add a Source first. \n")
            }
            println("List Source")
            print("Index (Number from 0 to ${sources.sourcesList.size - 1}): ")
            try {
                var readIndex = readln().toInt()
                println("\n" + sources.getFormatedSource(readIndex, false) + "\n")
            } catch (e: Exception) {
                println("Please enter a valid Number.\n")
            }

        } else if (option == "listallsources") {
            println()
            print(sources.listAllSources(false))

        } else if (option == "deleteallsources") {

            println("Delete all Sources")
            print("Are you sure? (y/n): ")
            val readSure = readln().lowercase()
            if (readSure == "y") {
                sources.sourcesList.clear()
                println("All Sources have been deleted. \n")
            } else {
                println("No Sources have been deleted. \n")
            }

        } else if (option == "save") {
            println("Save Sources")
            print("Are you sure? (y/n): ")
            val readSure = readln().lowercase()
            if (readSure == "y") {
                sources.saveSources()
                println("Sources have been saved. \n")
            } else {
                println("Sources have not been saved. \n")
            }
        } else {
            println("Please enter a valid Option. \n")
        }
    }
}

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
            return """
                Index: $index
                URL: ${currentsource["url"]}
                Title: ${currentsource["title"]}
                Author: ${currentsource["authorFirstName"]} ${currentsource["authorLastName"]}
                Release Year: ${currentsource["releaseYear"]}
                Date of Check: ${currentsource["date"]}
                """.trimIndent()

        } else {
            if (currentsource["authorLastName"]?.isEmpty() == false) {
                return "[] ${currentsource["authorLastName"]}, ${currentsource["authorFirstName"]} (${currentsource["releaseYear"]}): ${currentsource["title"]} URL: ${currentsource["url"]} [Stand: ${currentsource["date"]}]"
            } else {
                return "[] ${currentsource["authorFirstName"]} (${currentsource["releaseYear"]}): ${currentsource["title"]} URL: ${currentsource["url"]} [Stand: ${currentsource["date"]}]"
            }
        }
    }
    // Return All Sources as String
    fun listAllSources(flat: Boolean = true): String {
        var toString = ""

        for (index in 0..<sourcesList.size) {
            toString += getFormatedSource(index, flat) + "\n \n"
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
// Return current Date
fun currentDate(): String {
    return "${Calendar.getInstance().get(Calendar.DATE)}. ${Calendar.getInstance().get(Calendar.MONTH)}. ${Calendar.getInstance().get(Calendar.YEAR)}"
}

//returns true if specified file is empty
fun isFileEmpty(filePath: String): Boolean {
    return Files.size(Paths.get(filePath)) == 0L
}
