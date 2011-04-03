package com.herocraftonline.rightlegred.herolock;

import java.util.HashMap;

import javax.persistence.PersistenceException;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.herocraftonline.rightlegred.herolock.command.CommandManager;
import com.herocraftonline.rightlegred.herolock.command.commands.*;

public class HeroLock extends JavaPlugin{
    // HeroLock Stuff
    protected HashMap<String, String> lockCommand = new HashMap<String, String>();
    protected HashMap<String, String> unlockCommand = new HashMap<String, String>();

    // HeroLock Listeners
    private final HLPlayerListener playerListener = new HLPlayerListener(this);

    //Command Manager
    CommandManager commandManager;

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        registerEvents();
        setupDatabase();
        registerCommands();
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
        commandManager = new CommandManager();
        // Page 1
        commandManager.addCommand(new CommandLock(this));
        commandManager.addCommand(new CommandUnlock(this));

    }
}
