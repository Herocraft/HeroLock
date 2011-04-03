package com.herocraftonline.rightlegred.herolock;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.ContainerBlock;
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
            if(plugin.getLockCommands().containsKey(event.getPlayer().getName())){
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().eq("location", block.getLocation()).findUnique();
                if(chestCheck == null){
                    HeroChest newLock = new HeroChest();
                    newLock.setLocation(block.getLocation());
                    newLock.setPassword(plugin.getLockCommands().get(event.getPlayer().getName()));
                    newLock.setPlayer(event.getPlayer());
                    plugin.getDatabase().save(newLock);
                    event.getPlayer().sendMessage(ChatColor.RED + "Locked chest - With password: " + ChatColor.BLUE + plugin.getLockCommands().get(event.getPlayer().getName()));
                    plugin.getLockCommands().remove(event.getPlayer().getName());
                }else{
                    event.getPlayer().sendMessage("Sorry, that chest is already locked");
                }
            }else if(plugin.getUnlockCommands().containsKey(event.getPlayer().getName())){
                HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().eq("location", block.getLocation()).ieq("password", plugin.getUnlockCommands().get(event.getPlayer().getName())).findUnique();
                if(chestCheck == null){
                    event.getPlayer().sendMessage("Sorry, that password is incorrect for that chest");
                    event.setCancelled(true);
                    event.setUseInteractedBlock(Result.DENY);
                    return;
                }else{
                    return;
                }
            }
        }
    }
}


