package svcs

import java.io.File


/**
 * Created with IntelliJ IDEA.
$ Project: Version Control System
 * User: rodrigotroy
 * Date: 08-03-23
 * Time: 17:14
 */
class VersionControl {
    private var username = ""
    private val filesIndex = mutableListOf<String>()
    private val commitIndex = mutableSetOf<Pair<String, Int>>()
    private val log = mutableListOf<String>()

    init {
        if (!File(DIRECTORY).exists()) {
            File(DIRECTORY).mkdir()
        }

        if (!File("${DIRECTORY}/commits").exists()) {
            File("${DIRECTORY}/commits").mkdir()
        }

        if (File("${DIRECTORY}/log.txt").exists()) {
            log.addAll(File("${DIRECTORY}/log.txt").readLines())
        } else {
            File("${DIRECTORY}/log.txt").createNewFile()
        }

        if (File("${DIRECTORY}/config.txt").exists()) {
            username = File("${DIRECTORY}/config.txt").readText().replace("\n", "")
        } else {
            File("${DIRECTORY}/config.txt").createNewFile()
        }

        if (File("${DIRECTORY}/index.txt").exists()) {
            filesIndex.addAll(File("${DIRECTORY}/index.txt").readLines())
        } else {
            File("${DIRECTORY}/index.txt").createNewFile()
        }

        if (File("${DIRECTORY}/commits.txt").exists()) {
            commitIndex.addAll(File("${DIRECTORY}/commits.txt").readLines().map { it.split("|") }
                .map { it[0] to it[1].toInt() })
        } else {
            File("${DIRECTORY}/commits.txt").createNewFile()
        }
    }

    private fun commit(args: Array<String>) {
        if (args.size == 1) {
            println("Message was not passed.")
            return
        }

        var thereAreChanges = false
        if (filesIndex.isEmpty()) {
            println("Nothing to commit.")
            return
        } else {
            commitIndex.forEach {
                val file = File(it.first)
                if (file.readText().hashCode() != it.second) {
                    thereAreChanges = true
                }
            }
        }

        if (commitIndex.isEmpty() && filesIndex.isNotEmpty()) {
            thereAreChanges = true
        }

        if (thereAreChanges) {
            val commit = Commit(filesIndex)
            val uuid = commit.execute()
            val message = args[1]

            File("${DIRECTORY}/log.txt").appendText("commit $uuid|Author: $username|$message${System.lineSeparator()}")

            println("Changes are committed.")
        } else {
            println("Nothing to commit.")
        }
    }

    private fun checkout() {
        println("Restore a file.")
    }

    private fun log() {
        if (log.isEmpty()) {
            println("No commits yet.")
            return
        }

        log.reversed().forEach {
            println()
            it.split("|").forEach { println(it) }
            println()
        }
    }

    fun execute(args: Array<String>) {
        val command = args[0].trim()

        if (command.contains("help")) {
            printHelp()
            return
        }

        when (command) {
            "add" -> add(args)
            "commit" -> commit(args)
            "checkout" -> checkout()
            "log" -> log()
            "config" -> config(args)
            else -> println("'$command' is not a SVCS command.")
        }
    }

    private fun add(args: Array<String>) {
        if (args.size == 1) {
            if (filesIndex.isEmpty()) {
                println("Add a file to the index.")
            } else {
                println("Tracked files:")
                filesIndex.forEach { println(it) }
            }

            return
        }

        if (args.size == 2) {
            val file = File(args[1])
            if (file.exists()) {
                if (filesIndex.map { it }.contains(file.name)) {
                    println("The file '${file.name}' is already in the index.")
                } else {
                    filesIndex.add(file.name)
                    File("${DIRECTORY}/index.txt").appendText("${file.name}${System.lineSeparator()}")
                    println("The file '${file.name}' is tracked.")
                }
            } else {
                println("Can't find '${file.name}'.")
            }

            return
        }
    }

    private fun printHelp() {
        println("These are SVCS commands:")
        println("config     Get and set a username.")
        println("add        Add a file to the index.")
        println("log        Show commit logs.")
        println("commit     Save changes.")
        println("checkout   Restore a file.")
    }

    private fun config(args: Array<String>) {
        if (args.size == 1) {
            if (username.isNotBlank()) {
                println("The username is $username.")
            } else {
                println("Please, tell me who you are.")
            }

            return
        }

        if (args.size == 2) {
            username = args[1]
            File("${DIRECTORY}/config.txt").writeText(username.trim())
            println("The username is $username.")
            return
        }
    }

    companion object {
        const val DIRECTORY = "vcs"
    }
}
