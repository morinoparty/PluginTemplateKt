package party.morino.pluginname.velocity.commands

import com.velocitypowered.api.command.CommandSource
import net.kyori.adventure.text.Component
import org.incendo.cloud.velocity.VelocityCommandManager

class ExampleCommand(
    private val commandManager: VelocityCommandManager<CommandSource>,
) {
    fun register() {
        val command = commandManager.commandBuilder("pluginname")
            .handler { ctx ->
                ctx.sender().sendMessage(Component.text("Hello from PluginName!"))
            }
            .build()

        commandManager.command(command)
    }
}
