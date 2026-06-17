package com.jelo.api.command.argument;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EnumArgument<E extends Enum<E>> extends Argument<E> {

    private final Class<E> enumClass;

    public EnumArgument(String id, Class<E> enumClass) {
        super(id);
        this.enumClass = enumClass;
    }

    @Override
    public Optional<E> parse(CommandSender sender, String input) {
        try {
            return Optional.of(
                    Enum.valueOf(enumClass, input.toUpperCase())
            );
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<String> suggest(CommandSender sender, String currentInput) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .toList();
    }
}