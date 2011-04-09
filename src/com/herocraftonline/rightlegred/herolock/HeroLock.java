package com.herocraftonline.rightlegred.herolock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.ChatColor;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.herocraftonline.rightlegred.herolock.commands.*;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

@SuppressWarnings("unused")
public class HeroLock extends JavaPlugin {
    // HeroLock Stuff
    protected HashMap<String, String> lockCommand = new HashMap<String, String>();
    protected HashMap<String, String> unlockCommand = new HashMap<String, String>();
    protected HashMap<String, String> changeCommand = new HashMap<String, String>();

    // HeroLock Listeners
    private final HLPlayerListener playerListener = new HLPlayerListener(this);
    private final HLBlockListener blockListener = new HLBlockListener(this);

    // Permissions
    public PermissionHandler Permissions;
    public boolean hasPermissions;

    @Override
    public void onDisable() {
        getDatabase().endTransaction();
    }

    @Override
    public void onEnable() {
        registerEvents();
        setupDatabase();
        registerCommands();
        if (getServer().getPluginManager().getPlugin("Permissions") != null) {
            hasPermissions = true;
        } else {
            hasPermissions = false;
        }
    }

    public HashMap<String, String> getLockCommands() {
        return lockCommand;
    }

    public HashMap<String, String> getUnlockCommands() {
        return unlockCommand;

    }

    public HashMap<String, String> getChangeCommands() {
        return changeCommand;
    }

    public void registerEvents() {
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
        getCommand("lock").setExecutor(new CommandChestLock(this));
        getCommand("unlock").setExecutor(new CommandChestUnlock(this));
        getCommand("change").setExecutor(new CommandChestChangePassword(this));
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
                this.Permissions = ((Permissions) test).getHandler();
            } else {
                System.out.println("Permission system not detected, defaulting to OP");
            }
        }
    }
}
