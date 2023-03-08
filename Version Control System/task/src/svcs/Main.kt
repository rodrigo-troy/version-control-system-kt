package svcs

fun main(args: Array<String>) {
    //read first argument
    val command = args.getOrNull(0) ?: ""

    if (command.isBlank() || (args.size == 1 && command == "--help")) {
        printHelp()
        return
    } else if (command == "config") {
        println("Get and set a username.")
    } else if (command == "add") {
        println("Add a file to the index.")
    } else if (command == "log") {
        println("Show commit logs.")
    } else if (command == "commit") {
        println("Save changes.")
    } else if (command == "checkout") {
        println("Restore a file.")
    } else {
        println("'$command' is not a SVCS command.")
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
