package com.jelo.api.command;

import com.jelo.api.command.argument.Argument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MinecraftCommand extends Command {

    private final JeloCommand command;

    public MinecraftCommand(@NotNull JeloCommand command) {
        super(
                command.getName(),
                "",
                command.getUsage() == null ? "" : command.getUsage(),
                Arrays.asList(command.getAliases())
        );
        this.command = command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender,
                           @NotNull String commandLabel,
                           @NotNull String @NonNull [] args) {
        if (command.getPermission() != null
                && !command.getPermission().isBlank()
                && !sender.hasPermission(command.getPermission())) {
            sender.sendMessage(CommandMessagePreset.NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {
            if (command.getDefaultExecutor() != null) {
                command.getDefaultExecutor().run(sender, new CommandContext());
            } else {
                sender.sendMessage("§cUsage: /" + commandLabel + " <subcommand>");
            }
            return true;
        }

        CommandSyntax bestSyntax = null;
        CommandContext bestContext = null;
        int bestLiteralLength = -1;

        for (CommandSyntax syntax : command.getSyntaxes()) {

            if (syntax.getCondition() != null && !syntax.getCondition().check(sender)) {
                continue;
            }

            String[] literals = syntax.getLiteral().split(" ");

            if (args.length < literals.length) {
                continue;
            }

            boolean literalMatch = true;
            for (int i = 0; i < literals.length; i++) {
                if (!args[i].equalsIgnoreCase(literals[i])) {
                    literalMatch = false;
                    break;
                }
            }

            if (!literalMatch) {
                continue;
            }

            List<Argument<?>> parameters = syntax.getArguments();

            if (args.length - literals.length != parameters.size()) {
                continue;
            }

            CommandContext context = new CommandContext();
            boolean parseSuccess = true;

            for (int i = 0; i < parameters.size(); i++) {

                Argument<?> argument = parameters.get(i);

                Optional<?> parsed = argument.parse(sender, args[literals.length + i]);

                if (parsed.isEmpty()) {
                    parseSuccess = false;
                    break;
                }

                context.set(argument.getId(), parsed.get());
            }

            if (!parseSuccess) {
                continue;
            }

            if (literals.length > bestLiteralLength) {
                bestLiteralLength = literals.length;
                bestSyntax = syntax;
                bestContext = context;
            }
        }

        if (bestSyntax == null) {
            if (command.getDefaultExecutor() != null) {
                command.getDefaultExecutor().run(sender, new CommandContext());
            } else {
                sender.sendMessage("§cUnknown command.");
            }
            return true;
        }

        bestSyntax.getHandler().run(sender, bestContext);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) {
        if (args.length == 0) return List.of();

        List<String> completions = new ArrayList<>();
        String ongoingTypingWordInput = args[args.length - 1];
        int typingWordIndex = args.length - 1;

        for (CommandSyntax syntax : command.getSyntaxes()) {
            if (syntax.getCondition() != null && !syntax.getCondition().check(sender)) {
                continue;
            }

            String[] syntaxWords = syntax.getLiteral().split(" ");

            // CASE A: Autocomplete the next literal token in a nested command chain route path
            if (typingWordIndex < syntaxWords.length) {
                boolean matchesSoFar = true;
                for (int i = 0; i < typingWordIndex; i++) {
                    if (!args[i].equalsIgnoreCase(syntaxWords[i])) {
                        matchesSoFar = false;
                        break;
                    }
                }
                if (matchesSoFar) {
                    completions.add(syntaxWords[typingWordIndex]);
                }
                continue;
            }

            // CASE B: Autocomplete contextual argument values from specialized type parser blocks
            boolean completePathMatches = true;
            for (int i = 0; i < syntaxWords.length; i++) {
                if (!args[i].equalsIgnoreCase(syntaxWords[i])) {
                    completePathMatches = false;
                    break;
                }
            }

            if (completePathMatches) {
                int expectedArgLookupIndex = typingWordIndex - syntaxWords.length;
                List<Argument<?>> targetSignatureArgs = syntax.getArguments();

                if (expectedArgLookupIndex > targetSignatureArgs.size()) {
                    continue;
                }

                boolean valid = true;
                for (int i = 0; i < expectedArgLookupIndex; i++) {
                    Argument<?> arg = targetSignatureArgs.get(i);

                    String input = args[syntaxWords.length + i];

                    if (arg.parse(sender, input).isEmpty()) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    continue;
                }

                if (expectedArgLookupIndex < targetSignatureArgs.size()) {
                    Argument<?> currentParserNode = targetSignatureArgs.get(expectedArgLookupIndex);
                    completions.addAll(currentParserNode.suggest(sender, ongoingTypingWordInput));
                }
            }
        }

        return completions.stream()
                .distinct()
                .filter(s -> s.toLowerCase().startsWith(ongoingTypingWordInput.toLowerCase()))
                .toList();
    }

    private void sendSyntaxUsage(CommandSender sender, String commandLabel, CommandSyntax syntax) {
        StringBuilder builder = new StringBuilder("§cUsage: /").append(commandLabel).append(" ").append(syntax.getLiteral());
        for (Argument<?> arg : syntax.getArguments()) {
            builder.append(" <").append(arg.getId()).append(">");
        }
        sender.sendMessage(builder.toString());
    }
}