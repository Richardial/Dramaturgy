import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds


fun main() {
    var json = File("settingsDram.json").readText(Charsets.UTF_8)
    val mapper = jacksonObjectMapper()
    var settings : Setting = mapper.readValue(json,Setting::class.java)
    val url = settings.downloadUrl
    val downloadFolder  = File(url)
    val watchServiceDownloads = FileSystems.getDefault().newWatchService()
    val pathToWatch = downloadFolder.toPath()
    val pathKey = pathToWatch.register(watchServiceDownloads, StandardWatchEventKinds.ENTRY_CREATE)

    while (true) {
        val watchKey = watchServiceDownloads.take()

        for (event in watchKey.pollEvents()) {
            var file = File(url+"\\${event.context()}")
            println(file.path)
            Thread.sleep(800)
            if(file.extension != "opdownload"){
                Thread.sleep(800)
                FileClassification.redirect(file, settings.extensionsPath, settings.prefix, settings.blackList)
            }
        }

        if (!watchKey.reset()) {
            watchKey.cancel()
            watchServiceDownloads.close()
            break
        }
    }
    pathKey.cancel()
}