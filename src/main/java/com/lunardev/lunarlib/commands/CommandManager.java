package com.lunardev.lunarlib.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Static command manager
 */
public final class CommandManager {

    CommandManager() {
    }

    /**
     * Register command.
     *
     * @param plugin    the plugin
     * @param framework the framework
     */
    public static void registerCommand(@NotNull JavaPlugin plugin, @NotNull CommandFramework framework) {
        PluginCommand command = plugin.getCommand(framework.getCmdName());

        if (command == null) {
            Command cmd = new Command(
                    framework.getCmdName(),
                    framework.getDescription(),
                    framework.getUsageMessage(),
                    framework.getAliases()
            ) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                    return framework.execute(sender, this, commandLabel, args);
                }
            };
            cmd.setPermission(framework.getPermission());
            plugin.getServer().getCommandMap().register(plugin.getPluginMeta().getName(), cmd);
        } else {
            command.setExecutor(framework);
        }
    }

    /**
     * Unregister command.
     *
     * @param plugin  the plugin
     * @param cmdName the cmd name
     */
    public static void unregisterCommand(JavaPlugin plugin, String cmdName) {
        try {
            PluginCommand command = plugin.getCommand(cmdName);

            if (command != null) {
                command.unregister(plugin.getServer().getCommandMap());
            }
        } catch (Exception e) {
            // ignored
        }
    }

}