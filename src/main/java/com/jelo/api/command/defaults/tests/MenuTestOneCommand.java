package com.jelo.api.command.defaults.tests;

import com.jelo.api.command.JeloCommand;
import com.jelo.api.menu.Menu;
import com.jelo.api.menu.MenuSession;
import com.jelo.api.menu.content.MenuContent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuTestOneCommand extends JeloCommand {

    public MenuTestOneCommand() {
        super("menutest-1");

        Menu menu = Menu.builder("<yellow>Test 1", 6)
                .useSoundWhenOpen(true)
                .build();

        setDefaultExecutor(((commandSender, context) -> {
            if (commandSender instanceof Player player) {
                MenuSession session = menu.open(player);
                session.refresh();
            } else {
                commandSender.sendMessage("no");
            }
        }));
    }
}
