package me.pepsiplaya.endervaults;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class EnderChestListener implements Listener {
    private final EnderVault plugin;

    public EnderChestListener(EnderVault plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST && plugin.getConfig().getBoolean("replace-enderchest")) {
            event.setCancelled(true);

            if (event.getPlayer() instanceof Player) {
                Player player = (Player) event.getPlayer();
                VaultGUI.openEnderVault(player, plugin);
            }
        }
    }
}