import com.github.kittinunf.fuel.httpGet
import java.io.File


class HttpClient {
    private val session = File("src/main/resources/session.secret").readText()
    private val defaultParameters = arrayOf(Pair("Cookie", "session=$session"), Pair("User-Agent", "github.com/heimmat/adventOfCode by heimmat"))
    fun get(path: String) = path.httpGet().header(*defaultParameters).responseString().second.body().asString("text/plain")
}