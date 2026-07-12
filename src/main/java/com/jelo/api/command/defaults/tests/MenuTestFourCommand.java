package com.jelo.api.command.defaults.tests;

import com.jelo.api.command.JeloCommand;
import com.jelo.api.menu.Menu;
import com.jelo.api.menu.MenuSession;
import com.jelo.api.menu.content.MenuContent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuTestFourCommand extends JeloCommand {

    public MenuTestFourCommand() {
        super("menutest-4");

        Menu menu = Menu.builder("Jelo", 6)

                .takeable(false)
                .refreshInterval(20)
                .useSoundWhenOpen(true)

                .content()
                .addContent(MenuContent.of(new ItemStack(Material.STICK), 1, 1))
                .build()

                .header()
                .addContent(MenuContent.of(new ItemStack(Material.PLAYER_HEAD), 4, 1))
                .build()

                .footer()
                .addContent(MenuContent.of(new ItemStack(Material.BARRIER), 4, 1))
                .build()

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
