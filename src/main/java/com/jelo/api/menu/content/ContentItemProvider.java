package com.jelo.api.menu.content;

import com.jelo.api.menu.MenuSession;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface ContentItemProvider {

    ItemStack provide(MenuSession session);
}
