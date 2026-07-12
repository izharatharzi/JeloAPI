package com.jelo.api;

import com.jelo.api.command.defaults.MainCommand;
import com.jelo.api.command.defaults.tests.*;
import com.jelo.api.item.ability.AbilityListener;
import com.jelo.api.item.action.ActionListener;
import com.jelo.api.item.commands.ItemManagerCommand;
import com.jelo.api.item.defaults.SimpleItem;
import com.jelo.api.menu.listener.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private static Plugin instance;

    private JeloAPI jeloAPI;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

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
        getServer().getPluginManager().registerEvents(new ActionListener(jeloAPI), this);

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    private void setupItems() {
        jeloAPI.getItemManager().registerItem(new SimpleItem());
    }

    private void setupCommands() {
        MainCommand mainCommand = new MainCommand();
        ItemManagerCommand itemManagerCommand = new ItemManagerCommand(jeloAPI);

        jeloAPI.getCommandManager().registerCommand(this, itemManagerCommand);

        jeloAPI.getCommandManager().registerSubCommand(mainCommand, itemManagerCommand);
        jeloAPI.getCommandManager().registerCommand(this, mainCommand);

        // Registering test commands
        if (getConfig().getBoolean("commands.test-commands")) {
            jeloAPI.getCommandManager().registerCommand(this, new MenuTestOneCommand());
            jeloAPI.getCommandManager().registerCommand(this, new MenuTestTwoCommand());
            jeloAPI.getCommandManager().registerCommand(this, new MenuTestThreeCommand());
            jeloAPI.getCommandManager().registerCommand(this, new MenuTestFourCommand());
            jeloAPI.getCommandManager().registerCommand(this, new MenuTestFiveCommand());
        }
    }

    public static Plugin getInstance() {
        return instance;
    }
}
