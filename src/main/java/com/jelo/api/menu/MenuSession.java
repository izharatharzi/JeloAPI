package com.jelo.api.menu;

import com.jelo.api.Plugin;
import com.jelo.api.menu.content.MenuContent;
import com.jelo.api.menu.content.Position;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public final class MenuSession {

    private static final Plugin plugin = Plugin.getInstance();

    private final Menu menu;
    private final Player player;

    private final MenuHolder holder;
    private final ItemStack borderItem;

    private final List<MenuContent> contents = new ArrayList<>();
    private final Map<Integer, MenuContent> renderedContents = new HashMap<>();

    private Inventory inventory;
    private BukkitTask refreshTask;

    MenuSession(Menu menu, Player player) {
        this.menu = menu;
        this.player = player;
        this.holder = new MenuHolder(this);

        this.contents.addAll(menu.getContents());
        this.borderItem = new ItemStack(menu.getBorderMaterial());
    }

    /**
     * Open the inventory for player.
     */
    public void open() {
        inventory = Bukkit.createInventory(
                holder,
                menu.getInventorySize(),
                menu.getTitle()
        );
        holder.setInventory(inventory);

        render();

        player.openInventory(inventory);
        if (menu.isUseOpenSound()) {
            player.playSound(player.getLocation(), menu.getOpenSound(), 1f, 1f);
        }

        if (menu.isAutoRefresh()) {
            refreshTask = Bukkit.getScheduler().runTaskTimer(
                    plugin,
                    this::refresh,
                    menu.getRefreshInterval(),
                    menu.getRefreshInterval()
            );
        }
    }

    /**
     * Close the inventory from player.
     */
    public void close() {
        if (refreshTask != null) {
            refreshTask.cancel();
        }
    }

    /**
     * Render the inventory.
     */
    private void render() {
        inventory.clear();
        renderedContents.clear();

        renderBorder();
        renderContents();
    }

    /**
     * Refreshes the menu inventory.
     */
    public void refresh() {
        if (player.getOpenInventory().getTopInventory() != inventory) {
            close();
            return;
        }

        render();
    }

    /**
     * Add content to session.
     *
     * @param content The content
     */
    public void addContent(MenuContent content) {
        contents.add(content);
    }

    /**
     * Remove content from session.
     *
     * @param content The content
     */
    public void removeContent(MenuContent content) {
        contents.remove(content);
    }

    private void renderBorder() {
        int rows = menu.getRows();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < 9; x++) {

                boolean border =
                        x == 0 ||
                                x == 8 ||
                                y == 0 ||
                                y == rows - 1;

                if (!border) {
                    continue;
                }

                int slot = inventorySlot(x, y);
                if (inventory.getItem(slot) != null) continue;

                inventory.setItem(slot, borderItem);
            }
        }
    }

    private void renderContents() {
        for (MenuContent content : contents) {
            int slot = contentSlot(content.getPosition());

            inventory.setItem(slot, content.provide(this));
            renderedContents.put(slot, content);
        }
    }

    private int inventorySlot(int x, int y) {
        return y * 9 + x;
    }

    private int contentSlot(Position position) {
        int x = position.x();
        int y = position.y();

        if (!menu.isUseBorder()) {
            x--;
            y--;
        }

        return inventorySlot(x, y);
    }

    public Optional<MenuContent> getContent(int slot) {
        return Optional.ofNullable(renderedContents.get(slot));
    }

    public Map<Integer, MenuContent> getRenderedContents() {
        return Map.copyOf(renderedContents);
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Menu getMenu() {
        return menu;
    }
}
