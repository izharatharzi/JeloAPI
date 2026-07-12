package com.jelo.api.menu.content;

import com.jelo.api.menu.MenuSession;
import com.jelo.api.menu.content.click.MenuClickHandler;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represent object as content of menu.
 */
public final class MenuContent {

    private final ItemStack itemStack;
    private final ContentItemProvider contentItemProvider;
    private final Position position;

    private MenuClickHandler clickHandler;

    private Boolean takeable;

    public MenuContent(ItemStack itemStack, ContentItemProvider contentItemProvider, Position position) {
        this.itemStack = itemStack;
        this.contentItemProvider = contentItemProvider;
        this.position = position;
    }

    public static MenuContent of(ItemStack item, int x, int y) {
        return new MenuContent(item, null, new Position(x, y));
    }

    public static MenuContent dynamic(ContentItemProvider contentItemProvider, int x, int y) {
        return new MenuContent(null, contentItemProvider, new Position(x, y));
    }

    public ItemStack provide(MenuSession session) {
        if (contentItemProvider != null) {
            return contentItemProvider.provide(session);
        }

        return itemStack;
    }

    public MenuContent onClick(@NotNull MenuClickHandler handler) {
        this.clickHandler = handler;
        return this;
    }

    public MenuContent takeable(Boolean takeable) {
        this.takeable = takeable;
        return this;
    }

    @Deprecated
    public ItemStack getItemStack() {
        return itemStack;
    }

    public Position getPosition() {
        return position;
    }

    public Boolean isTakeable() {
        return takeable;
    }

    public MenuClickHandler getClickHandler() {
        return clickHandler;
    }
}
