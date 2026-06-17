package com.jelo.api.command.argument;

import com.jelo.api.JeloAPI;
import com.jelo.api.item.CustomItem;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public class CustomItemArgument extends Argument<CustomItem> {

    private final JeloAPI jeloAPI;

    public CustomItemArgument(JeloAPI jeloAPI, String id) {
        super(id);
        this.jeloAPI = jeloAPI;
    }

    @Override
    public Optional<CustomItem> parse(CommandSender sender, String input) {
        return jeloAPI.getItemManager().getByName(input);
    }

    @Override
    public List<String> suggest(CommandSender sender, String currentInput) {
        return jeloAPI.getItemManager().getCustomItems().stream().map(CustomItem::itemName).toList();
    }
}