package com.jelo.api.command.defaults.tests;

import com.jelo.api.command.JeloCommand;
import com.jelo.api.menu.Menu;
import com.jelo.api.menu.MenuSession;
import com.jelo.api.menu.content.MenuContent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuTestFiveCommand extends JeloCommand {

    public MenuTestFiveCommand() {
        super("menutest-5");

        Menu menu = Menu.builder("Jelo", 6)

                .takeable(false)
                .refreshInterval(20)
                .useSoundWhenOpen(true)
                .border(Material.GRAY_STAINED_GLASS_PANE)

                .content()
                .addContent(MenuContent.of(new ItemStack(Material.STICK), 1, 1))
                .build()

                .header()
                .addContent(MenuContent.of(new ItemStack(Material.PLAYER_HEAD), 4, 1))
                .build()

                .footer()
                .addContent(MenuContent.of(new ItemStack(Material.BARRIER), 4, 1))
                .build()

                .leftBar()
                .addContent(MenuContent.of(new ItemStack(Material.NAME_TAG), 1, 1))
                .build()

                .rightBar()
                .addContent(MenuContent.of(new ItemStack(Material.NAME_TAG), 1, 1))
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
