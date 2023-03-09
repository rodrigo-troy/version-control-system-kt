package svcs

fun main(args: Array<String>) {
    val versionControl = VersionControl()
    versionControl.execute(if (args.isNotEmpty()) args else arrayOf("/help"))
}

