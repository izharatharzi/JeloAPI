package com.jelo.api.item.commands;

import com.jelo.api.JeloAPI;
import com.jelo.api.command.JeloCommand;
import com.jelo.api.command.SubCommand;
import com.jelo.api.item.CustomItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemManagerCommand extends JeloCommand {

    private final JeloAPI jeloAPI;

    public ItemManagerCommand(JeloAPI jeloAPI) {
        super("itemmanager");
        this.jeloAPI = jeloAPI;

        setPermission("jeloapi.command.itemmanager");

        setDefaultExecutor((sender, ctx) -> sender.sendMessage("§cWrong syntax. Use /itemmanager give-item"));
    }

    @SubCommand(
            name = "give-item",
            permission = "jeloapi.command.giveitem"
    )
    public void onGiveItemCommand(CommandSender sender, Player target, CustomItem item, int amount) {
        for (int i = 0; i < amount; i++) {
            jeloAPI.getItemManager().giveItem(target, item);
        }
    }
}