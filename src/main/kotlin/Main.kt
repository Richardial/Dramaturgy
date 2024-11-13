import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds


fun main() {
    val delimiter = if (System.getProperty("os.name").contains("win", ignoreCase = true)) "\\" else "/"
    watchDownloads(delimiter)

}


fun watchDownloads(delimiter: String) {
    val json = File("settingsDram.json").readText(Charsets.UTF_8)
    val settings = Json.decodeFromString<Setting>(json)

    val url = settings.downloadUrl
    val downloadFolder = File(url)

    val watchServiceDownloads = FileSystems.getDefault().newWatchService()
    val pathToWatch = downloadFolder.toPath()
    val pathKey = pathToWatch.register(watchServiceDownloads, StandardWatchEventKinds.ENTRY_CREATE)


    while (true) {
        val watchKey = watchServiceDownloads.take()
        for (event in watchKey.pollEvents()) {
            val file = File(url + "$delimiter${event.context()}")
            Thread.sleep(1000)
            if (file.extension != "tmp")
                println(file.path)
            FileClassification.redirect(file, settings.extensionsPath, settings.prefix, settings.blackList, delimiter)
        }

        if (!watchKey.reset()) {
            watchKey.cancel()
            watchServiceDownloads.close()
            break
        }
    }
    pathKey.cancel()
}