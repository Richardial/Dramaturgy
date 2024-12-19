import kotlinx.serialization.Serializable

@Serializable
class Setting(var downloadUrl : String, var extensionsPath : ArrayList<ExtensionPath>,
              var prefix : ArrayList<Prefix>, var blackList : ArrayList<String>) {
}