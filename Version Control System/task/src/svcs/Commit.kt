package svcs

import java.io.File
import java.util.*

class Commit(private val filesIndex: MutableList<String>) {
    private val randomUUID: String = UUID.randomUUID().toString()

    fun execute(): String {
        if (!File("${VersionControl.DIRECTORY}/commits/$randomUUID").exists()) {
            File("${VersionControl.DIRECTORY}/commits/$randomUUID").mkdir()
        }

        for (fileName in filesIndex) {
            val file = File(fileName)
            file.copyTo(File("${VersionControl.DIRECTORY}/commits/$randomUUID/$fileName"))
            val hashCode = file.readText().hashCode()
            File("${VersionControl.DIRECTORY}/commits.txt").appendText("$fileName|$hashCode${System.lineSeparator()}")
        }

        return randomUUID
    }
}
