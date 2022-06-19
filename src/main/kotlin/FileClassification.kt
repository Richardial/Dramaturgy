import java.io.File

class FileClassification {

    companion object{
        fun redirect(
            file: File, extensions: ArrayList<ExtensionPath>, prefix: ArrayList<Prefix>,
            black: ArrayList<String>,
        ){
            if(file.name[0].equals('-') && file.name[2].equals(' ')){
                val tempPrefix = file.name.subSequence(0,2)
                println(tempPrefix)
                for(pref in prefix){
                    if(tempPrefix.equals(pref.prefix)){
                        file.renameTo(File(pref.url+ cleanName(file.name,black)))
                    }else{
                        redirectByExtension(file, extensions, black)
                    }
                }
            }else{
                redirectByExtension(file, extensions, black)
            }

        }

        private fun redirectByExtension(file: File, extensions: ArrayList<ExtensionPath>, black: ArrayList<String>){
            for(ext in extensions){
                if(file.extension in ext.extension){
                    file.renameTo(File(ext.url+cleanName(file.name, black)))
                }
            }
        }

        private fun cleanName(name : String, black: ArrayList<String>) : String{
            var newName = name

            if(name.startsWith("-")){
                newName = newName.drop(3)
            }

            for(b in black){
                newName = newName.replace(b,"")
            }
            return newName
        }

    }







}