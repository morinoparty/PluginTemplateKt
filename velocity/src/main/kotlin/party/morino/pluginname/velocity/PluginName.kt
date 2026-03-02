package party.morino.pluginname.velocity

import com.google.inject.Inject
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.velocity.VelocityCommandManager
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.getOrNull
import org.koin.dsl.module
import org.slf4j.Logger
import party.morino.pluginname.common.PluginNameCommon
import party.morino.pluginname.velocity.commands.ExampleCommand

@Plugin(
    id = "pluginname",
    name = "PluginName",
    version = "1.0.0",
    description = "A Minecraft plugin template",
    authors = ["morinoparty"],
)
class PluginName @Inject constructor(
    private val server: ProxyServer,
    private val logger: Logger,
) {

    @Subscribe
    @Suppress("UnusedParameter")
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        setupKoin()
        PluginNameCommon.init()

        val commandManager = VelocityCommandManager<CommandSource>(
            server.pluginManager.ensurePluginContainer(this),
            server,
            ExecutionCoordinator.asyncCoordinator(),
            SenderMapper.identity(),
        )

        ExampleCommand(commandManager).register()

        logger.info("PluginName has been enabled!")
    }

    @Subscribe
    @Suppress("UnusedParameter")
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        logger.info("PluginName has been disabled!")
    }

    /**
     * Koin DI コンテナの初期化
     */
    private fun setupKoin() {
        if (getOrNull() != null) {
            return
        }

        val appModule = module {
            single<ProxyServer> { server }
            single<org.slf4j.Logger> { this@PluginName.logger }
        }

        getOrNull() ?: GlobalContext.startKoin {
            modules(appModule)
        }
    }
}
