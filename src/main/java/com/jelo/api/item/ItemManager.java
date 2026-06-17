package com.jelo.api.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface ItemManager {

    /**
     * Get all the registered custom items.
     *
     * @return {@link Collection} of custom items
     */
    Collection<CustomItem> getCustomItems();

    /**
     * Get custom item by name.
     *
     * @param name The name of the custom item
     * @return {@link Optional} of custom item
     */
    Optional<CustomItem> getByName(@NotNull String name);

    /**
     * get custom item by {@link ItemStack} with trying to check
     * whether the item have the exact CUSTOM_ITEM_NAME in
     * {@link org.bukkit.persistence.PersistentDataContainer}.
     *
     * @param itemStack The item stack
     * @return {@link Optional} of custom item
     */
    Optional<CustomItem> getByItemStack(@NotNull ItemStack itemStack);

    /**
     * Register new custom item.
     *
     * @param customItem The custom item
     */
    void registerItem(@NotNull CustomItem customItem);

    /**
     * Unregister specific custom item.
     *
     * @param customItem The custom item
     */
    void unregisterItem(@NotNull CustomItem customItem);

    void unregisterItems();

    /**
     * Give specific custom item to specific player.
     *
     * @param player The player
     * @param customItem The custom item
     */
    void giveItem(@NotNull Player player, @NotNull CustomItem customItem);

}
