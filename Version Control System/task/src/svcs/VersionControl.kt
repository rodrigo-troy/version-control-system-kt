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

    init {
        //create directory "vcs"
        if (!File("vcs").exists()) {
            File("vcs").mkdir()
        }

        //add a file in vcs


        if (File("vcs/config.txt").exists()) {
            username = File("vcs/config.txt").readText()
        } else {
            File("vcs/config.txt").createNewFile()
        }

        if (File("vcs/index.txt").exists()) {
            filesIndex.addAll(File("vcs/index.txt").readLines())
        } else {
            File("vcs/index.txt").createNewFile()
        }
    }

    private fun commit() {
        println("Save changes.")
    }

    private fun checkout() {
        println("Restore a file.")
    }

    private fun log() {
        println("Show commit logs.")
    }

    fun execute(args: Array<String>) {
        val command = args[0].trim()

        if (command.contains("help") || args.isEmpty()) {
            printHelp()
            return
        }

        when (command) {
            "add" -> add(args)
            "commit" -> commit()
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
                if (filesIndex.contains(file.name)) {
                    println("The file '${file.name}' is already in the index.")
                } else {
                    filesIndex.add(file.name)
                    File("vcs/index.txt").appendText(file.name + "\n")
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
            File("vcs/config.txt").writeText(username)
            println("The username is $username.")
            return
        }
    }
}
