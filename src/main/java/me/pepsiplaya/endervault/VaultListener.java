package me.pepsiplaya.endervault;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class VaultListener implements Listener {

    private final EnderVault plugin;
    private final VaultData vaultData;

    public VaultListener(EnderVault plugin) {
        this.plugin = plugin;
        this.vaultData = new VaultData(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ender-vault-title"));
        if (event.getWhoClicked() instanceof Player && event.getView().getTitle().equals(title)) {
            Player player = (Player) event.getWhoClicked();
            int clickedSlot = event.getRawSlot();

            if (event.getClickedInventory() != event.getView().getTopInventory()) {
                return;
            }

            int highestPermission = 0;
            for (int i = 1; i <= event.getView().getTopInventory().getSize(); i++) {
                if (player.hasPermission("endervaults.size." + i)) {
                    highestPermission = i;
                }
            }

            if (clickedSlot < highestPermission) {
                // Player has access to this slot
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ender-vault-title"));
        if (event.getPlayer() instanceof Player && event.getView().getTitle().equals(title)) {
            Player player = (Player) event.getPlayer();
            Inventory vault = event.getInventory();

            int size = vault.getSize();
            int highestPermission = 0;
            for (int i = 1; i <= size; i++) {
                if (player.hasPermission("endervaults.size." + i)) {
                    highestPermission = i;
                }
            }

            VaultData vaultData = new VaultData(plugin);
            vaultData.saveVaultContents(player, vault, highestPermission);
        }
    }
}
