import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path

class FileClassification {

    companion object {
        fun redirect(
            file: File, extensions: ArrayList<ExtensionPath>, prefix: ArrayList<Prefix>,
            black: ArrayList<String>,delimiter: String
        ){
            val patternPref = "^-[A-Z|a-z]".toRegex()
            val hasPrefix = patternPref.find(file.name)?.value

            if (hasPrefix != null) {
                val tempPrefix = file.name.subSequence(0, 2)
                for (pref in prefix) {
                    if (tempPrefix == pref.prefix) {
                        file.renameTo(File(pref.url + cleanName(file.name, black)))
                        print(file.path)
                    }
                }
            }else{
                redirectByExtension(file, extensions, black, delimiter)
            }

        }

        private fun redirectByExtension(file: File, extensions: ArrayList<ExtensionPath>,
                                        blacklist: ArrayList<String>, delimiter: String){
            for(ext in extensions){
                if(file.extension in ext.extension){
                    val extraFolder = getFolderInName(file.name,1)
                    if(extraFolder != null){
                        Files.createDirectories(Path(ext.url+extraFolder))
                        moveFiles(file,ext.url+extraFolder+delimiter+cleanName(file.name, blacklist))
                    }else{
                        moveFiles(file,ext.url+cleanName(file.name, blacklist))
                    }
                }
            }
        }

        private fun cleanName(name: String, blacklist: ArrayList<String>): String {
            val folder = getFolderInName(name, 0)
            return name
                .removePrefix("-")
                .let { if (folder != null) it.replace(folder,"") else it }
                .let{ cleanName ->
                    blacklist.fold(cleanName) {acc, item -> acc.replace(item, "")}
                }
        }

        private fun getFolderInName(name: String, value: Int): String? {
            val pattern = "\\(([^()]*)\\)".toRegex()
            return pattern.find(name)?.groupValues?.get(value)
        }

        private fun moveFiles(file: File, destination: String){
            val finalFile = File(destination)
            try {
                Files.copy(file.toPath(), finalFile.toPath(),StandardCopyOption.REPLACE_EXISTING)
                if(file.delete()){
                    println("Archivo eliminado")
                }else{
                    println("Error eliminando archivo")
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }


}