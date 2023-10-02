import java.util.*

var wantToExit = false

fun selectOption(sources: SourceSaver) {

    print("Choose an option (exit, addsource, copysource, deletesource, deleteallsources, listsource, listallsources, save): ")
    val option = readln().lowercase()
    println()

    if (option == "exit") {
        sources.saveSources()
        wantToExit = true

    } else if (option == "addsource") {
        println("Add Source")

        print("URL: ")
        val readUrl = readln()

        print("Title: ")
        val readTitle = readln()

        print("Author Firstname: ")
        val readAuthorFirstName = readln()

        print("Author Lastname (leave empty in unknown): ")
        val readAuthorLastName = readln()

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
        println("Source has been added. (Index: ${sources.sourcesList.size-1})")

    } else if (option == "deletesource") {

        if (sources.sourcesList.size == 0) {
            println("You need to add a Source first.")

        } else {
            println("Delete Source")
            print("Index (Number from 0 to ${sources.sourcesList.size-1}): ")
            try {
                val readIndex = readln().toInt()
                sources.removeSource(readIndex)
                println("Source $readIndex has been removed.")
            } catch (e: Exception) {
                println("Please enter a valid Number.")
            }
        }

    } else if (option == "listsource") {

        if (sources.sourcesList.size == 0) {
            println("You need to add a Source first.")
        } else {
            println("List Source")
            print("Index (Number from 0 to ${sources.sourcesList.size - 1}): ")
            try {
                val readIndex = readln().toInt()
                println("\n" + sources.getFormatedSource(readIndex, false))
            } catch (e: Exception) {
                println("Please enter a valid Number.")
            }
        }

    } else if (option == "listallsources") {
        print(sources.listAllSources(false))

    } else if (option == "deleteallsources") {

        println("Delete all Sources")
        print("Are you sure? (y/n): ")
        val readSure = readln().lowercase()
        if (readSure == "y") {
            sources.sourcesList.clear()
            println("All Sources have been deleted.")
        } else {
            println("No Sources have been deleted.")
        }

    } else if (option == "save") {
        println("Save Sources")
        print("Are you sure? (y/n): ")
        val readSure = readln().lowercase()
        if (readSure == "y") {
            sources.saveSources()
            println("Sources have been saved.")
        } else {
            println("Sources have not been saved.")
        }
    } else if (option == "copysource") {

        if (sources.sourcesList.size == 0) {
            println("You need to add a Source first.")
        } else {
            println("Copy Source")
            print("Index (Number from 0 to ${sources.sourcesList.size - 1}): ")
            try {
                val readIndex = readln().toInt()
                copyToClipboard(sources.getFormatedSource(readIndex, true))
                println("Source $readIndex has been copied to clipboard.")
            } catch (e: Exception) {
                println("Please enter a valid Number.")
            }
        }
    } else {
        println("Please enter a valid Option.")
    }

    println()
    println("Hello World")
}