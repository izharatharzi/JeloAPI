package com.jelo.api.item;

import com.jelo.api.util.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    private ItemBuilder(Material material) {
        this(material, 1);
    }

    public static ItemBuilder builder(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder builder(Material material) {
        return new ItemBuilder(material);
    }

    public ItemBuilder name(String input) {
        this.itemMeta.displayName(MiniMessageUtil.miniMessage.deserialize(input));
        return this;
    }

    public ItemBuilder name(Component component) {
        this.itemMeta.displayName(component);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
