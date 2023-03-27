package me.pepsiplaya.endervaults;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class VaultData {
    private final EnderVault plugin;

    public VaultData(EnderVault plugin) {
        this.plugin = plugin;
    }

    public void saveVaultContents(Player player, Inventory vault, int highestPermission) {
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File playerFile = new File(dataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

        playerData.set("name", player.getName());
        playerData.set("uuid", player.getUniqueId().toString());

        for (int i = 0; i < vault.getSize(); i++) {
            if (i < highestPermission) {
                ItemStack item = vault.getItem(i);
                playerData.set("slots." + i, item);
            }
        }

        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save vault data for player " + player.getName());
            e.printStackTrace();
        }
    }

    public void loadVaultContents(Player player, Inventory vault, int highestPermission) {
        File dataFolder = new File(plugin.getDataFolder(), "data");
        File playerFile = new File(dataFolder, player.getUniqueId().toString() + ".yml");
        if (!playerFile.exists()) {
            return;
        }

        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
        for (int i = 0; i < vault.getSize(); i++) {
            if (i < highestPermission) {
                ItemStack item = playerData.getItemStack("slots." + i, null);
                if (item != null) {
                    vault.setItem(i, item);
                }
            }
        }
    }
}
