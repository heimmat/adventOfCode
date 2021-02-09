import java.io.File

class Input(val year: Int, val day: Int) {

    val asList: List<String> by lazy { asList() }
    val asString: String by lazy { asString() }

    private fun asFile(): File {
        val fileDir = "src/main/resources/cache/$year"
        val dirInfo = File(fileDir)
        if (!(dirInfo.exists()  && dirInfo.isDirectory)) {
            dirInfo.mkdirs()
        }
        val fileName = "$fileDir/$day.txt"
        val fileInfo = File(fileName)
        if (!(fileInfo.exists() && fileInfo.canRead())) {
            val httpClient = HttpClient()
            val inputFromBackend = httpClient.get("https://adventofcode.com/$year/day/$day/input")
            fileInfo.writeText(inputFromBackend)
        }
        return fileInfo
    }

    private fun asList(): List<String> = asFile().readLines()

    private fun asString(): String {
        return asFile().readText()
//        val fileDir = "src/main/resources/cache/$year"
//        val dirInfo = File(fileDir)
//        if (!(dirInfo.exists()  && dirInfo.isDirectory)) {
//            dirInfo.mkdir()
//        }
//        val fileName = "$fileDir/$day.txt"
//        val fileInfo = File(fileName)
//        val input: String
//        if (!(fileInfo.exists() && fileInfo.canRead())) {
//            val inputFromBackend = httpClient.get("https://adventofcode.com/$year/day/$day/input")
//            fileInfo.writeText(inputFromBackend)
//            input = inputFromBackend
//        } else {
//            input = fileInfo.readText()
//        }
//        return input


    }
}