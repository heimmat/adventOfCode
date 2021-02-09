import com.github.kittinunf.fuel.httpGet
import java.io.File


class HttpClient {
    private val session = File("src/main/resources/session.secret").readText()
    private val defaultParameters = Pair("Cookie", "session=$session")
    fun get(path: String) = path.httpGet().header(defaultParameters).responseString().second.body().asString("text/plain")
}