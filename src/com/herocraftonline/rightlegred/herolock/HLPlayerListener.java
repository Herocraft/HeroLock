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

    public HLPlayerListener(HeroLock plugin) {
        this.plugin = plugin;
    }

    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block.getState() instanceof ContainerBlock) {
            Player p = event.getPlayer();
            HashMap<String, String> lockCommand = plugin.getLockCommands();
            HashMap<String, String> unlockCommand = plugin.getUnlockCommands();
            if(lockCommand.containsKey(p.getName())){
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().ieq("location", block.getLocation().toString()).findUnique();
                if(chestCheck == null){
                    HeroChest newLock = new HeroChest();
                    newLock.setLocation(block.getLocation().toString());
                    newLock.setPassword(lockCommand.get(p.getName()));
                    newLock.setPlayer(p);
                    newLock.setWorldName(p.getWorld().toString());
                    plugin.getDatabase().save(newLock);
                    p.sendMessage(ChatColor.RED + "Locked chest - With password: " + ChatColor.BLUE + lockCommand.get(p.getName()));
                    lockCommand.remove(p.getName());
                }else{
                    p.sendMessage("Sorry, that chest is already locked");
                }
            }else{
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().ieq("location", block.getLocation().toString()).findUnique();
                if(chestCheck == null){
                    return;
                }else{
                    if(unlockCommand.containsKey(p.getName())){
                        if(unlockCommand.get(p.getName()).equalsIgnoreCase(chestCheck.getPassword())){
                            
                        }else{
                            p.sendMessage("Wrong password!");
                            event.setCancelled(true);
                            event.setUseInteractedBlock(Result.DENY);   
                        }
                    }else{
                        p.sendMessage("Sorry, that chest is locked!");
                        event.setCancelled(true);
                        event.setUseInteractedBlock(Result.DENY);
                    }
                }
            }
        }
    }
}


