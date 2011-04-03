package com.herocraftonline.rightlegred.herolock.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.rightlegred.herolock.HeroLock;
import com.herocraftonline.rightlegred.herolock.command.BaseCommand;

public class CommandUnlock extends BaseCommand{

    public CommandUnlock(HeroLock plugin) {
        super(plugin);
        name = "Unlock";
        description = "Opens a chest using the password";
        usage = "/unlock <password>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("unlock");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(plugin.getLockCommands().containsKey((p.getName()))){
                String password = plugin.getLockCommands().get(p.getName());
                sender.sendMessage("Switching from Lock Mode with password: " + ChatColor.BLUE + password);
                plugin.getLockCommands().remove(p.getName());
            }
            plugin.getUnlockCommands().put(((Player) sender).getName(), args[0]);
            sender.sendMessage(ChatColor.RED + "Activated Unlock Mode - With Password: " + ChatColor.BLUE + args[0]);
        }
    }
}
