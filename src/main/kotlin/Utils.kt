import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

// Return current Date
fun currentDate(): String {
    return "${Calendar.getInstance().get(Calendar.DATE)}. ${Calendar.getInstance().get(Calendar.MONTH)}. ${
        Calendar.getInstance().get(
            Calendar.YEAR)}"
}

//returns true if specified file is empty
fun isFileEmpty(filePath: String): Boolean {
    return Files.size(Paths.get(filePath)) == 0L
}

//copy text to clipboard
fun copyToClipboard(text: String) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val transferable = StringSelection(text)
    clipboard.setContents(transferable, null)
}
