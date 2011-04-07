package com.herocraftonline.rightlegred.herolock;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.ContainerBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class HLPlayerListener extends PlayerListener {

    private final HeroLock plugin;
    private final Utils util;

    public HLPlayerListener(HeroLock plugin) {
        this.plugin = plugin;
        this.util = new Utils(plugin);
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        Player p = event.getPlayer();

        if (block.getState() instanceof ContainerBlock) {
            HashMap<String, String> lockCommand = plugin.getLockCommands();
            HashMap<String, String> unlockCommand = plugin.getUnlockCommands();
            HashMap<String, String> changeCommand = plugin.getChangeCommands();
            if (lockCommand.containsKey(p.getName())) {
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().ieq("location", block.getLocation().toString()).findUnique();
                if (chestCheck == null) {
                    event.setCancelled(true);
                    event.setUseInteractedBlock(Result.DENY);
                    HeroChest newLock = new HeroChest();
                    newLock.setLocation(block.getLocation().toString());
                    newLock.setPassword(util.md5String(lockCommand.get(p.getName())));
                    newLock.setPlayer(p);
                    newLock.setWorldName(p.getWorld().toString());
                    plugin.getDatabase().save(newLock);
                    p.sendMessage(ChatColor.RED + "Locked chest - With password: " + ChatColor.BLUE + lockCommand.get(p.getName()));
                    lockCommand.remove(p.getName());
                } else {
                    p.sendMessage(ChatColor.RED + "HeroLock: Sorry, that chest is already locked");
                }
            } else if (unlockCommand.containsKey(p.getName())) {
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().ieq("location", block.getLocation().toString()).findUnique();
                if (chestCheck == null) {
                    return;
                } else {
                    if (unlockCommand.containsKey(p.getName())) {
                        if (util.md5String(unlockCommand.get(p.getName())).equalsIgnoreCase(chestCheck.getPassword())) {

                        } else {
                            p.sendMessage(ChatColor.RED + "HeroLock: Wrong password!");
                            event.setCancelled(true);
                            event.setUseInteractedBlock(Result.DENY);
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "HeroLock: Sorry, that chest is locked!");
                        event.setCancelled(true);
                        event.setUseInteractedBlock(Result.DENY);
                    }
                }
            } else if (changeCommand.containsKey(p.getName())) {
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().ieq("location", block.getLocation().toString()).findUnique();
                if (chestCheck != null) {
                    if (chestCheck.playerName.equalsIgnoreCase(p.getName())) {
                        chestCheck.setPassword(util.md5String(changeCommand.get(p.getName())));
                        plugin.getDatabase().save(chestCheck);
                        event.setCancelled(true);
                        event.setUseInteractedBlock(Result.DENY);
                        p.sendMessage(ChatColor.RED + "Locked chest - With password: " + ChatColor.BLUE + changeCommand.get(p.getName()));
                        changeCommand.remove(p.getName());
                    }
                }
            } else {
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().ieq("location", block.getLocation().toString()).findUnique();
                if (chestCheck != null) {
                    p.sendMessage(ChatColor.RED + "HeroLock: Sorry, that chest is locked!");
                    event.setCancelled(true);
                    event.setUseInteractedBlock(Result.DENY);
                }
            }
        }
    }
}
