import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class ExtensionPath(var extension: ArrayList<String>,var url: String){
}
