package com.jelo.api.command.argument;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TargetArgument extends Argument<String> {

    public TargetArgument(String id) {
        super(id);
    }

    @Override
    public List<String> suggest(CommandSender sender, String currentInput) {
        List<String> list = new ArrayList<>(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
        list.add("all");
        return list;

    }

    @Override
    public Optional<String> parse(CommandSender sender, String input) {
        if (input.equalsIgnoreCase("all")) {
            return Optional.of("all");
        }

        Player player = Bukkit.getPlayerExact(input);
        if (player != null) {
            return Optional.of(player.getName());
        }

        return Optional.empty();
    }
}
