package com.herocraftonline.rightlegred.herolock.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.rightlegred.herolock.HeroLock;
import com.herocraftonline.rightlegred.herolock.command.BaseCommand;
public class CommandLock extends BaseCommand{

    public CommandLock(HeroLock plugin) {
        super(plugin);
        name = "lock";
        description = "Locks a chest";
        usage = "/lock <password>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("lock");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(plugin.getUnlockCommands().containsKey((p.getName()))){
                String password = plugin.getUnlockCommands().get(p.getName());
                sender.sendMessage("Switching from Lock Mode with password: " + ChatColor.BLUE + password);
                plugin.getUnlockCommands().remove(p.getName());
            }
            
            plugin.getLockCommands().put(((Player) sender).getName(), args[0]);
            sender.sendMessage(ChatColor.RED + "Activated Lock Mode - With Password: " + ChatColor.BLUE + args[0]);
        }
    }
}
