import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class FileClassification {

    companion object{
        fun redirect(
            file: File, extensions: ArrayList<ExtensionPath>, prefix: ArrayList<Prefix>,
            black: ArrayList<String>,
        ){
            val patternPref = "^-[A-Z|a-z]".toRegex()
            val hasPrefix = patternPref.find(file.name)?.value

            if(hasPrefix != null){
                val tempPrefix = file.name.subSequence(0,2)
                for(pref in prefix){
                    if(tempPrefix == pref.prefix){
                        file.renameTo(File(pref.url+ cleanName(file.name,black)))
                    }
                }
            }else{
                redirectByExtension(file, extensions, black)
            }

        }

        private fun redirectByExtension(file: File, extensions: ArrayList<ExtensionPath>, black: ArrayList<String>){
            for(ext in extensions){
                if(file.extension in ext.extension){
                    val extraFolder = getFolderInName(file.name,1)
                    if(extraFolder != null){
                        Files.createDirectories(Path(ext.url+extraFolder))
                        file.renameTo(File(ext.url+extraFolder+"\\"+cleanName(file.name, black)))
                    }else{
                        file.renameTo(File(ext.url+cleanName(file.name, black)))
                    }
                }
            }
        }

        private fun cleanName(name : String, black: ArrayList<String>) : String{
            var newName = name
            val fold = getFolderInName(name,0)

            if(name.startsWith("-")){
                newName = newName.drop(3)
            }

            if(fold != null){
                newName = newName.replace(fold,"")
            }

            for(b in black){
                newName = newName.replace(b,"")
            }
            return newName
        }

        private fun getFolderInName(name: String, value: Int): String? {
            val pattern = "\\(([^()]*)\\)".toRegex()
            return pattern.find(name)?.groupValues?.get(value)
        }

    }
}