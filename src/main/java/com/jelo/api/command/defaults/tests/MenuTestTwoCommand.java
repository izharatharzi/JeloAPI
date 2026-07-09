package com.jelo.api.command.defaults.tests;

import com.jelo.api.command.JeloCommand;
import com.jelo.api.item.ItemBuilder;
import com.jelo.api.menu.Menu;
import com.jelo.api.menu.MenuSession;
import com.jelo.api.menu.content.MenuContent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MenuTestTwoCommand extends JeloCommand {

    public MenuTestTwoCommand() {
        super("menutest-2");

        Menu menu = Menu.builder("<yellow>test-2", 5)
                .useSoundWhenOpen(true)
                .border(Material.GRAY_STAINED_GLASS_PANE)
                .addContent(
                        MenuContent
                                .dynamic(session -> ItemBuilder.builder(Material.PLAYER_HEAD)
                                        .name("<green>Online players: <white>" + Bukkit.getOnlinePlayers().size()).build(), 4, 2)
                                .takeable(false)
                )
                .refreshInterval(20)
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
