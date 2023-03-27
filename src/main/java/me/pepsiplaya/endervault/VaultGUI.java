package me.pepsiplaya.endervault;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
public class VaultGUI {
    public static void openEnderVault(Player player, EnderVault plugin) {
        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ender-vault-title"));
        int maxRows = 5;

        int highestPermission = 0;
        for (int i = 1; i <= maxRows * 9; i++) {
            if (player.hasPermission("endervaults.size." + i)) {
                highestPermission = i;
            }
        }

        int rows = (highestPermission + 8) / 9; // Calculate rows based on the highest permission
        int size = rows * 9;
        Inventory enderVault = Bukkit.createInventory(player, size, title);

        String lockedSlotMaterialName = plugin.getConfig().getString("locked-slot.material");
        Material lockedSlotMaterial = Material.matchMaterial(lockedSlotMaterialName);
        if (lockedSlotMaterial == null) {
            lockedSlotMaterial = Material.GRAY_STAINED_GLASS_PANE;
        }

        String lockedSlotDisplayName = plugin.getConfig().getString("locked-slot.display-name");

        ItemStack placeholder = new ItemStack(lockedSlotMaterial);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', lockedSlotDisplayName));
        placeholder.setItemMeta(placeholderMeta);

        for (int i = 0; i < size; i++) {
            enderVault.setItem(i, placeholder);
        }

        for (int i = 0; i < size; i++) {
            if (i < highestPermission) {
                enderVault.setItem(i, new ItemStack(Material.AIR));
            } else {
                enderVault.setItem(i, placeholder);
            }
        }

        VaultData vaultData = new VaultData(plugin);
        vaultData.loadVaultContents(player, enderVault, highestPermission);

        player.openInventory(enderVault);

        if (plugin.getConfig().getBoolean("open-vault-sound.enabled")) {
            String soundName = plugin.getConfig().getString("open-vault-sound.sound");
            Sound openVaultSound = Sound.valueOf(soundName);
            float volume = (float) plugin.getConfig().getDouble("open-vault-sound.volume");
            float pitch = (float) plugin.getConfig().getDouble("open-vault-sound.pitch");

            if (openVaultSound != null) {
                player.playSound(player.getLocation(), openVaultSound, volume, pitch);
            }
        }
    }

    public static void checkPlayerVault(Player admin, String targetName, EnderVault plugin) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            admin.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ender-vault-title")) + " - " + target.getName();
        int maxRows = 5;
        int rows = 1;
        int highestPermission = 0;

        for (int i = 1; i <= maxRows * 9; i++) {
            if (target.isOnline() && target.getPlayer().hasPermission("endervaults.size." + i)) {
                highestPermission = i;
            }
        }

        rows = (highestPermission - 1) / 9 + 1;
        int size = rows * 9;
        Inventory enderVault = Bukkit.createInventory(admin, size, title);

        String lockedSlotMaterialName = plugin.getConfig().getString("locked-slot.material");
        Material lockedSlotMaterial = Material.matchMaterial(lockedSlotMaterialName);
        if (lockedSlotMaterial == null) {
            lockedSlotMaterial = Material.GRAY_STAINED_GLASS_PANE;
        }

        String lockedSlotDisplayName = plugin.getConfig().getString("locked-slot.display-name");

        ItemStack placeholder = new ItemStack(lockedSlotMaterial);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', lockedSlotDisplayName));
        placeholder.setItemMeta(placeholderMeta);

        for (int i = 0; i < size; i++) {
            enderVault.setItem(i, placeholder);
        }

        for (int i = 0; i < size; i++) {
            if (i < highestPermission) {
                enderVault.setItem(i, new ItemStack(Material.AIR));
            } else {
                enderVault.setItem(i, placeholder);
            }
        }

        VaultData vaultData = new VaultData(plugin);
        vaultData.loadVaultContents(target.getPlayer(), enderVault, highestPermission);

        admin.openInventory(enderVault);
    }
}
