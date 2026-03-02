package party.morino.pluginname.paper.commands

import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.incendo.cloud.paper.PaperCommandManager

class ExampleCommand(
    private val commandManager: PaperCommandManager<CommandSourceStack>,
) {
    fun register() {
        val command = commandManager.commandBuilder("pluginname")
            .handler { ctx ->
                ctx.sender().sender.sendMessage(Component.text("Hello from PluginName!"))
            }
            .build()

        commandManager.command(command)
    }
}
