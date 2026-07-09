package com.jelo.api.menu.listener;

import com.jelo.api.menu.Menu;
import com.jelo.api.menu.MenuHolder;
import com.jelo.api.menu.MenuSession;
import com.jelo.api.menu.content.MenuContent;
import com.jelo.api.menu.content.click.MenuClickContext;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onTakeItem(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof MenuHolder holder)) {
            return;
        }

        if (event.getClickedInventory() != event.getView().getTopInventory()) return;

        MenuSession session = holder.getSession();
        Menu menu = session.getMenu();

        MenuContent content = session.getContent(event.getRawSlot()).orElse(null);

        boolean takeable = menu.isTakeable();
        if (content != null && content.isTakeable() != null) {
            takeable = content.isTakeable();
        }

        event.setCancelled(!takeable);

        if (content != null && content.getClickHandler() != null) {
            content.getClickHandler()
                    .handle(new MenuClickContext(
                            (Player) event.getWhoClicked(),
                            session,
                            content,
                            event
                    ));
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof MenuHolder holder)) {
            return;
        }

        holder.getSession().close();
    }
}
