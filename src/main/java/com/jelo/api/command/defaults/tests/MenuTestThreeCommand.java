package com.jelo.api.command.defaults.tests;

import com.jelo.api.command.JeloCommand;
import com.jelo.api.menu.Menu;
import com.jelo.api.menu.MenuSession;
import com.jelo.api.menu.content.MenuContent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuTestThreeCommand extends JeloCommand {

    public MenuTestThreeCommand() {
        super("menutest-3");

        Menu menu = Menu.builder("<yellow>test-3", 6)
                .useSoundWhenOpen(true)
                .border(Material.GRAY_STAINED_GLASS_PANE)
                .addContent(
                        MenuContent.of(new ItemStack(Material.STICK), 1, 1)
                                .takeable(true)
                )
                .takeable(false)
                .build();

        setDefaultExecutor(((commandSender, context) -> {
            if (commandSender instanceof Player player) {
                MenuSession ignored = menu.open(player);
            } else {
                commandSender.sendMessage("no");
            }
        }));
    }
}
