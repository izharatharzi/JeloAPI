package com.jelo.api.command.argument;

import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public class IntegerArgument extends Argument<Integer> {
    public IntegerArgument(String id) { super(id); }

    @Override
    public List<String> suggest(CommandSender sender, String currentInput) {
        return List.of("1", "2", "3", "4", "5", "10", "20", "64");
    }

    @Override
    public Optional<Integer> parse(CommandSender sender, String input) {
        try { return Optional.of(Integer.parseInt(input)); }
        catch (NumberFormatException e) { return Optional.empty(); }
    }
}