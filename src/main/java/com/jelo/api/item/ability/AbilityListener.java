package com.jelo.api.item.ability;

import com.jelo.api.JeloAPI;
import com.jelo.api.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class AbilityListener implements Listener {

    private final JeloAPI jeloAPI;

    public AbilityListener(JeloAPI jeloAPI) {
        this.jeloAPI = jeloAPI;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        ItemStack itemStack = event.getItem();
        if (itemStack == null) return;

        Optional<CustomItem> optional = jeloAPI.getItemManager().getByItemStack(itemStack);
        if (optional.isEmpty()) return;

        CustomItem customItem = optional.get();
        AbilityType abilityType = getAbilityType(player, action);
        if (abilityType == null) return;

        customItem.executeAbility(abilityType, player, itemStack);
    }

    private @Nullable AbilityType getAbilityType(Player player, Action action) {
        boolean isSneaking = player.isSneaking();

        return switch (action) {
            case LEFT_CLICK_AIR   -> isSneaking ? AbilityType.SNEAK_LEFT_CLICK_AIR   : AbilityType.LEFT_CLICK_AIR;
            case LEFT_CLICK_BLOCK -> isSneaking ? AbilityType.SNEAK_LEFT_CLICK_BLOCK : AbilityType.LEFT_CLICK_BLOCK;
            case RIGHT_CLICK_AIR  -> isSneaking ? AbilityType.SNEAK_RIGHT_CLICK_AIR  : AbilityType.RIGHT_CLICK_AIR;
            case RIGHT_CLICK_BLOCK-> isSneaking ? AbilityType.SNEAK_RIGHT_CLICK_BLOCK: AbilityType.RIGHT_CLICK_BLOCK;
            default -> null;
        };
    }
}
