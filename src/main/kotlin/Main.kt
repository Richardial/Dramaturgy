import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds


fun main() {
    watchDownloads()
}

fun watchDownloads() {

    val json = File("settingsDram.json").readText(Charsets.UTF_8)
    val settings = Json.decodeFromString<Setting>(json)

    val url = settings.downloadUrl
    val downloadFolder = File(url)

    val watchServiceDownloads = FileSystems.getDefault().newWatchService()
    val pathToWatch = downloadFolder.toPath()
    val pathKey = pathToWatch.register(watchServiceDownloads, StandardWatchEventKinds.ENTRY_CREATE)

    var os: Boolean? = null

    if (System.getProperty("os.name").startsWith("Windows")) {
        os = true
    } else {
        os = false
    }




    while (true) {
        val watchKey = watchServiceDownloads.take()
        var file: File? = null
        for (event in watchKey.pollEvents()) {
            if (os) {
                file = File(url + "\\${event.context()}")
            } else {
                file = File(url + "/${event.context()}")
            }
            Thread.sleep(1000)
            if (file.extension != "tmp")
                println(file.path)
            FileClassification.redirect(file, settings.extensionsPath, settings.prefix, settings.blackList,os)
        }

        if (!watchKey.reset()) {
            watchKey.cancel()
            watchServiceDownloads.close()
            break
        }
    }
    pathKey.cancel()
}