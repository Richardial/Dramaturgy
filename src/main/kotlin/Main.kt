import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds


fun main() {
    watchDownloads()
}

fun watchDownloads(){
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
            val file = File(url+"\\${event.context()}")
            Thread.sleep(1000)
            if(file.extension != "tmp")
                println(file.path)
            FileClassification.redirect(file, settings.extensionsPath, settings.prefix, settings.blackList)
        }

        if (!watchKey.reset()) {
            watchKey.cancel()
            watchServiceDownloads.close()
            break
        }
    }
    pathKey.cancel()
}