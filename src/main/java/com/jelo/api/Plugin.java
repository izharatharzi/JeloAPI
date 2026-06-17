package com.jelo.api;

import com.jelo.api.command.defaults.MainCommand;
import com.jelo.api.item.ability.AbilityListener;
import com.jelo.api.item.commands.ItemManagerCommand;
import com.jelo.api.item.defaults.SimpleItem;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private JeloAPI jeloAPI;

    @Override
    public void onEnable() {
        this.jeloAPI = new JeloAPIImpl(this);

        APIProvider.set(jeloAPI);

        registerListeners();
        setupItems();
        setupCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        jeloAPI.getItemManager().unregisterItems();
        jeloAPI.getCommandManager().unregisterCommands();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new AbilityListener(jeloAPI), this);
    }

    private void setupItems() {
        jeloAPI.getItemManager().registerItem(new SimpleItem());
    }

    private void setupCommands() {
        MainCommand mainCommand = new MainCommand(this);
        ItemManagerCommand itemManagerCommand = new ItemManagerCommand(jeloAPI);

        jeloAPI.getCommandManager().registerCommand(this, itemManagerCommand);

        jeloAPI.getCommandManager().registerSubCommand(mainCommand, itemManagerCommand);
        jeloAPI.getCommandManager().registerCommand(this, mainCommand);
    }
}
