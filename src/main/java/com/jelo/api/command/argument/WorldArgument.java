package com.jelo.api.command.argument;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public class WorldArgument extends Argument<World> {
    public WorldArgument(String id) {
        super(id);
    }

    @Override
    public List<String> suggest(CommandSender sender, String currentInput) {
        return Bukkit.getWorlds().stream().map(World::getName).toList();
    }

    @Override
    public Optional<World> parse(CommandSender sender, String input) {
        return Optional.ofNullable(Bukkit.getWorld(input));
    }
}
