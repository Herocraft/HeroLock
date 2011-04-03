package com.herocraftonline.rightlegred.herolock;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class HLBlockListener extends BlockListener{
    private HeroLock plugin;

    public HLBlockListener(HeroLock plugin){
        this.plugin = plugin;
    }

    public void onBlockBreak(BlockBreakEvent event){
        HeroChest chestCheck = plugin.getDatabase().find(HeroChest.class).where().ieq("location", event.getBlock().getLocation().toString()).findUnique();
        if(chestCheck != null){
            if(!event.getPlayer().getName().equalsIgnoreCase(chestCheck.getPlayerName())){
                event.setCancelled(true);
            }
        }
    }
}
