package party.morino.pluginname.api

interface PluginNameAPI {
    companion object {
        private var instance: PluginNameAPI? = null

        fun getInstance(): PluginNameAPI {
            return checkNotNull(instance) { "PluginNameAPI is not initialized" }
        }

        fun setInstance(api: PluginNameAPI) {
            instance = api
        }
    }
}
