import kotlinx.serialization.Serializable

@Serializable
data class Prefix(var prefix : String, var url : String) {
}