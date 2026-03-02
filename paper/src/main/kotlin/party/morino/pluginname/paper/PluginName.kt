package party.morino.pluginname.paper

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.getOrNull
import org.koin.dsl.module
import party.morino.pluginname.common.PluginNameCommon

open class PluginName : SuspendingJavaPlugin() {

    override suspend fun onEnableAsync() {
        setupKoin()
        PluginNameCommon.init()
        logger.info("${pluginMeta.name} v${pluginMeta.version} has been enabled!")
    }

    override suspend fun onDisableAsync() {
        logger.info("${pluginMeta.name} has been disabled!")
    }

    /**
     * Koin DI コンテナの初期化
     * テスト環境では既に初期化済みの場合があるため、チェックを行う
     */
    private fun setupKoin() {
        if (getOrNull() != null) {
            return
        }

        val appModule = module {
            single<PluginName> { this@PluginName }
        }

        getOrNull() ?: GlobalContext.startKoin {
            modules(appModule)
        }
    }
}
