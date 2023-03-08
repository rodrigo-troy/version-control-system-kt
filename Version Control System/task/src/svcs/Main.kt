package svcs

fun main(args: Array<String>) {
    val versionControl = VersionControl()
    versionControl.execute(args.getOrNull(0) ?: "")
}

