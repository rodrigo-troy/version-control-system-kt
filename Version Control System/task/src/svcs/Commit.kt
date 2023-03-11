package svcs

import java.io.File
import java.util.*

class Commit(private val filesIndex: MutableList<Pair<String, Int>>) {
    private val randomUUID: String = UUID.randomUUID().toString()

    fun execute(): String {
        if (!File("${VersionControl.DIRECTORY}/commits/$randomUUID").exists()) {
            File("${VersionControl.DIRECTORY}/commits/$randomUUID").mkdir()
        }

        for (file in filesIndex) {
            File(file.first).copyTo(File("${VersionControl.DIRECTORY}/commits/$randomUUID/$file"))
        }

        return randomUUID
    }
}
