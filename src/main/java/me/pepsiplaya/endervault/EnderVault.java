package me.pepsiplaya.endervault;

import org.bukkit.plugin.java.JavaPlugin;

public final class EnderVault extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfiguration();
        this.getCommand("ev").setExecutor(new VaultCommand(this));
        getServer().getPluginManager().registerEvents(new VaultListener(this), this);
        getServer().getPluginManager().registerEvents(new EnderChestListener(this), this);
    }

    @Override
    public void onDisable() {
        // Save player vaults on plugin disable
    }

    public void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("ender-vault-title", "&0E&5n&ad&ce&4r&5 &0V&5a&au&cl&4t&5");
        getConfig().addDefault("replace-enderchest", true);
        getConfig().addDefault("locked-slot.material", "GRAY_STAINED_GLASS_PANE");
        getConfig().addDefault("locked-slot.display-name", "&7No Permission");
        getConfig().addDefault("open-vault-sound.enabled", true);
        getConfig().addDefault("open-vault-sound.sound", "BLOCK_ENDER_CHEST_OPEN");
        getConfig().addDefault("open-vault-sound.volume", 1.0);
        getConfig().addDefault("open-vault-sound.pitch", 1.0);
        saveConfig();
    }
}
