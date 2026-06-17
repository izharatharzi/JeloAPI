package com.jelo.api;

import com.jelo.api.command.manager.CommandManager;
import com.jelo.api.item.ItemManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public interface JeloAPI {

    @NotNull Logger getLogger();

    @NotNull ItemManager getItemManager();

    @NotNull CommandManager getCommandManager();
}
