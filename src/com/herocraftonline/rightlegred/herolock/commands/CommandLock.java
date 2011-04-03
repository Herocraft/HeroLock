package com.herocraftonline.rightlegred.herolock.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.rightlegred.herolock.HeroLock;
public class CommandLock implements CommandExecutor {
    private final HeroLock plugin;

    public CommandLock(HeroLock plugin) {
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

        Player p = (Player) sender;
        if (!plugin.Permissions.has(p, "herolock.lock")) {
            return false;
        }
        if(plugin.getUnlockCommands().containsKey((p.getName()))){
            String password = plugin.getUnlockCommands().get(p.getName());
            sender.sendMessage("Switching from Lock Mode with password: " + ChatColor.BLUE + password);
            plugin.getUnlockCommands().remove(p.getName());
        }
        
        plugin.getLockCommands().put(((Player) sender).getName(), args[0]);
        sender.sendMessage(ChatColor.RED + "Activated Lock Mode - With Password: " + ChatColor.BLUE + args[0]);
        return true;
    }
}
