package com.herocraftonline.rightlegred.herolock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.herocraftonline.rightlegred.herolock.commands.*;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class HeroLock extends JavaPlugin{
    // HeroLock Stuff
    protected HashMap<String, String> lockCommand = new HashMap<String, String>();
    protected HashMap<String, String> unlockCommand = new HashMap<String, String>();

    // HeroLock Listeners
    private final HLPlayerListener playerListener = new HLPlayerListener(this);
    private final HLBlockListener blockListener = new HLBlockListener(this);

    // Permissions
    public static PermissionHandler Permissions;


    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        registerEvents();
        setupDatabase();
        registerCommands();
        if(getServer().getPluginManager().getPlugin("Permissions") != null){
            // Does Have
        }else{
            // Doesn't Have
        }
    }

    public HashMap<String, String> getLockCommands(){
        return lockCommand;
    }

    public HashMap<String, String> getUnlockCommands(){
        return unlockCommand;
    }

    public void registerEvents(){
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Highest, this);
        pm.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Highest, this);
    }

    private void setupDatabase() {
        try {
            getDatabase().find(HeroChest.class).findRowCount();
        } catch (PersistenceException ex) {
            System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }

    private void registerCommands() {
        // Page 1
        getCommand("lock").setExecutor(new CommandLock(this));
        getCommand("unlock").setExecutor(new CommandUnlock(this));

    }
    
    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(HeroChest.class);
        return list;
    }
    
    private void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions)test).getHandler();
            } else {
                System.out.println("Permission system not detected, defaulting to OP");
            }
        }
    }
}
