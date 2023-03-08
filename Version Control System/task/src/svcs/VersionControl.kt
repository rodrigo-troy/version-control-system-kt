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
    var username = ""

    init {
        if (File("config.txt").exists()) {
            username = File("config.json").readText()
        }
    }

    fun add() {
        println("Add a file to the index.")
    }

    fun commit() {
        println("Save changes.")
    }

    fun checkout() {
        println("Restore a file.")
    }

    fun log() {
        println("Show commit logs.")
    }

    fun config() {
        println("Get and set a username.")
    }

    fun execute(input: String) {
        val args = input.split(" ")
        val command = args[0]

        when (command) {
            "add" -> add()
            "commit" -> commit()
            "checkout" -> checkout()
            "log" -> log()
            "config" -> config()
            else -> println("'$command' is not a SVCS command.")
        }
    }


    fun printHelp() {
        println("These are SVCS commands:")
        println("config     Get and set a username.")
        println("add        Add a file to the index.")
        println("log        Show commit logs.")
        println("commit     Save changes.")
        println("checkout   Restore a file.")
    }


    fun config(args: Array<String>) {
        when (args.size) {
            1 -> {
                println("Please, tell me who you are.")
            }

            2 -> {
                println("The username is ${args[1]}")
                //Store a username in config.txt

            }

            else -> {
                println("Too many arguments.")
            }
        }
    }
}
