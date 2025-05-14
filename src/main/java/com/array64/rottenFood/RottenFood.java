package com.array64.rottenFood;

import com.array64.rottenFood.commands.SetFoodTimerCommand;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RottenFood extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        RotScheduler.start(this);
        getCommand("setRottenTimer").setExecutor(new SetFoodTimerCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
