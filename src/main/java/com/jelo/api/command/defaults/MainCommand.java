package com.jelo.api.command.defaults;

import com.jelo.api.Plugin;
import com.jelo.api.command.JeloCommand;
import com.jelo.api.command.SubCommand;
import com.jelo.api.util.MiniMessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand extends JeloCommand {

    private final Plugin plugin;

    public MainCommand(Plugin plugin) {
        super("jeloapi", new String[]{"japi"});

        this.plugin = plugin;

        setPermission("jeloapi.command.main");
        setUsage("/jeloapi help");

        setDefaultExecutor(((commandSender, ctx) -> {
            commandSender.sendMessage("/jeloapi help");
        }));
    }

    @SubCommand(
            name = "reload-plugin",
            permission = "jeloapi.plugin.reload"
    )
    public void onReloadPluginCommand(@NotNull CommandSender commandSender) {
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getPluginManager().enablePlugin(plugin);
        commandSender.sendMessage(MiniMessageUtil.miniMessage.deserialize("<green>JeloAPI is successfully reloaded"));
    }
}
