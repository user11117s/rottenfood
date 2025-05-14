package com.array64.rottenFood.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SetFoodTimerCommand implements CommandExecutor {
    JavaPlugin plugin;
    public SetFoodTimerCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if(args.length > 0) {
                try {
                    int seconds = Integer.valueOf(args[0]);
                    if(seconds > 0) {
                        plugin.getConfig().set("foodBestBefore", seconds);
                        p.sendMessage("Food rot timer has been set to " + seconds + " second(s).");
                    }
                    else
                        throw new NumberFormatException();
                }
                catch(NumberFormatException e) {
                    p.sendMessage(ChatColor.RED + "You must specify a positive integer.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You need to specify what you want to set the timer to.");
            }
        }
        return true;
    }
}
