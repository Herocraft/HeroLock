package com.herocraftonline.rightlegred.herolock.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.rightlegred.herolock.HeroLock;

public class CommandChestUnlock implements CommandExecutor {
    private final HeroLock plugin;

    public CommandChestUnlock(HeroLock plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player == null) {
            return true;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "I don't know where you are!");
            return true;
        } else if (args.length < 1) {
            return false;
        }
        if (plugin.hasPermissions) {

            if (!plugin.Permissions.has(player, "herolock.chest.unlock")) {
                return false;
            }
        }

        Player p = (Player) sender;

        if (plugin.getLockCommands().containsKey((p.getName()))) {
            String password = plugin.getLockCommands().get(p.getName());
            sender.sendMessage(ChatColor.RED + "Switching from Lock Mode with password: " + ChatColor.BLUE + password);
            plugin.getLockCommands().remove(p.getName());
        }

        if (plugin.getChangeCommands().containsKey((p.getName()))) {
            String password = plugin.getChangeCommands().get(p.getName());
            sender.sendMessage(ChatColor.RED + "Switching from Change Password Mode with password: " + ChatColor.BLUE + password);
            plugin.getChangeCommands().remove(p.getName());
        }

        plugin.getUnlockCommands().put(((Player) sender).getName(), args[0]);
        sender.sendMessage(ChatColor.RED + "Activated Unlock Mode - With Password: " + ChatColor.BLUE + args[0]);

        return true;
    }
}
