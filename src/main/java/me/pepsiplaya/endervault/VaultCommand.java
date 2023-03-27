package me.pepsiplaya.endervault;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VaultCommand implements CommandExecutor {

    private final EnderVault plugin;

    public VaultCommand(EnderVault plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("open")) {
                if (player.hasPermission("endervaults.command.open")) {
                    VaultGUI.openEnderVault(player, plugin);
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                }
                return true;
            } else if (args[0].equalsIgnoreCase("check") && args.length == 2) {
                if (player.hasPermission("endervaults.command.check")) {
                    VaultGUI.checkPlayerVault(player, args[1], plugin);
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                }
                return true;
            }
        }

        return false;
    }
}
