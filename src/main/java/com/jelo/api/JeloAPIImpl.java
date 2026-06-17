package com.jelo.api;

import com.jelo.api.command.manager.CommandManager;
import com.jelo.api.command.manager.CommandManagerImpl;
import com.jelo.api.item.ItemManager;
import com.jelo.api.item.ItemManagerImpl;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeloAPIImpl implements JeloAPI {

    private final Logger LOGGER = LoggerFactory.getLogger(JeloAPIImpl.class);

    private final ItemManager itemManager;
    private final CommandManager commandManager;

    public JeloAPIImpl(Plugin plugin) {
        this.itemManager = new ItemManagerImpl(this, plugin);
        this.commandManager = new CommandManagerImpl(this);
    }

    @Override
    public @NotNull Logger getLogger() {
        return LOGGER;
    }

    @Override
    public @NotNull ItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public @NotNull CommandManager getCommandManager() {
        return commandManager;
    }
}
