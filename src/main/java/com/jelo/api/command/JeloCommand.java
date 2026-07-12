package com.jelo.api.command;

import com.jelo.api.command.argument.Argument;
import com.jelo.api.command.condition.CommandCondition;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class JeloCommand {

    private final String name;
    private final String[] aliases;

    private String permission;
    private String usage;

    private @Nullable CommandHandler defaultExecutor;
    private @Nullable CommandCondition condition;

    private final List<CommandSyntax> syntaxes = new ArrayList<>();

    public JeloCommand(String name, String[] aliases) {
        this.name = name;
        this.aliases = aliases;

    }

    public CommandSyntax addSyntax(CommandHandler handler, String subLiteral, Argument<?>... args) {
        CommandSyntax syntax = new CommandSyntax(handler, subLiteral, List.of(args));
        syntaxes.add(syntax);
        return syntax; // Returning this lets us chain configurations!
    }

    public CommandSyntax addSyntax(CommandHandler handler, Argument<?>... arguments) {
        return addSyntax(handler, null, arguments);
    }

    public JeloCommand(String name) {
        this(name, new String[0]);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public @Nullable CommandCondition getCondition() {
        return condition;
    }

    public void setCondition(@Nullable CommandCondition condition) {
        this.condition = condition;
    }

    public @Nullable CommandHandler getDefaultExecutor() {
        return defaultExecutor;
    }

    public void setDefaultExecutor(@Nullable CommandHandler defaultExecutor) {
        this.defaultExecutor = defaultExecutor;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public List<CommandSyntax> getSyntaxes() {
        return syntaxes;
    }
}
