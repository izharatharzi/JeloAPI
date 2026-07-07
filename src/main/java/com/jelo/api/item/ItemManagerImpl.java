package com.jelo.api.item;

import com.jelo.api.JeloAPI;
import com.jelo.api.util.MiniMessageUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ItemManagerImpl implements ItemManager {

    private final JeloAPI jeloAPI;

    private final Map<String, CustomItem> customItems = new HashMap<>();
    private final Map<CustomItem, ItemStack> customItemItemStackMap = new HashMap<>();

    private final NamespacedKey namespacedKey;

    public ItemManagerImpl(JeloAPI jeloAPI, @NotNull Plugin plugin) {
        this.jeloAPI = jeloAPI;
        this.namespacedKey = new NamespacedKey(
                plugin,
                "CUSTOM_ITEM_NAME"
        );
    }

    public Map<String, CustomItem> getCustomItemsMap() {
        return customItems;
    }

    public Map<CustomItem, ItemStack> getCustomItemItemStackMap() {
        return customItemItemStackMap;
    }

    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }

    @Override
    public Collection<CustomItem> getCustomItems() {
        return Collections.unmodifiableCollection(customItems.values());
    }

    @Override
    public Optional<CustomItem> getByName(@NotNull String name) {
        return customItems.containsKey(name) ?
                Optional.of(customItems.get(name)) :
                Optional.empty();
    }

    @Override
    public Optional<CustomItem> getByItemStack(@NotNull ItemStack itemStack) {
        return customItems.values().stream().filter(customItem ->
                itemStack.getItemMeta() != null &&
                itemStack.getItemMeta()
                        .getPersistentDataContainer()
                        .has(namespacedKey) &&
                itemStack.getItemMeta()
                        .getPersistentDataContainer()
                        .getOrDefault(
                                namespacedKey,
                                PersistentDataType.STRING,
                                "generic")
                        .equals(customItem.itemName()))
                .findFirst();
    }

    @Override
    public void registerItem(@NotNull CustomItem customItem) {
        if (getByName(customItem.itemName()).isPresent()) {
            jeloAPI.getLogger().debug("Custom item: {} is already registered. (Skip)", customItem.itemName());
            return;
        }

        String itemName = customItem.itemName();

        ItemStack itemStack = customItem.init();
        itemStack.editMeta(itemMeta -> itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, itemName));

        customItem.registerAbilities();
        customItem.registerActions();

        customItems.put(itemName, customItem);
        customItemItemStackMap.put(customItem, itemStack);

        jeloAPI.getLogger().info("Custom item: {} is successfully registered", itemName);
    }

    @Override
    public void unregisterItem(@NotNull CustomItem customItem) {
        if (getByName(customItem.itemName()).isEmpty()) {
            jeloAPI.getLogger().debug("Custom item: {} is not found (UNREGISTER ACTION). (Skipped)", customItem.itemName());
            return;
        }
        String itemName = customItem.itemName();
        customItems.remove(itemName, customItem);
        customItemItemStackMap.remove(customItem);

        jeloAPI.getLogger().info("Custom item: {} is successfully unregistered", itemName);
    }

    @Override
    public void unregisterItems() {
        jeloAPI.getLogger().info("Unregistering all custom items...");

        for (CustomItem customItem : new ArrayList<>(customItems.values())) {
            unregisterItem(customItem);
        }
    }

    @Override
    public void giveItem(@NotNull Player player, @NotNull CustomItem customItem) {
        String itemName = customItem.itemName();
        ItemStack itemStack = customItemItemStackMap.get(customItem).clone();
        player.getInventory().addItem(itemStack);
        player.sendMessage(MiniMessageUtil.miniMessage.deserialize("<green>You received <white><bold>" + itemName));

        jeloAPI.getLogger().info("Player: {} received {}", player.getUniqueId(), itemName);
    }
}
