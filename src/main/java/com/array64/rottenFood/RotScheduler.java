package com.array64.rottenFood;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class RotScheduler {
    static BukkitTask task;
    static NamespacedKey rottenIn;
    static JavaPlugin plugin;
    public static void start(JavaPlugin plugin) {
        RotScheduler.plugin = plugin;
        rottenIn = new NamespacedKey(plugin, "rotten_in");
        new BukkitRunnable() {
            @Override
            public void run() {
                RotScheduler.updateFood();
            }
        }.runTaskTimer(plugin, 60, 20); // Give players 3 seconds to breathe
    }
    static void updateFood() {
        for(Player p : Bukkit.getOnlinePlayers()) { // Go through every player
            Inventory inventory = p.getInventory();
            for(ItemStack item : inventory.getContents()) { // Go through every item
                updateOneItem(item);
            }
            updateOneItem(p.getItemOnCursor());
        }
    }
    static void updateOneItem(ItemStack item) {
        if(item == null) return;
        if(item.getType() == Material.ROTTEN_FLESH || !item.getType().isEdible()) return; // Only affect foods that are not rotten
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer data = meta.getPersistentDataContainer();
        int rottenTime = data.getOrDefault(rottenIn, PersistentDataType.INTEGER, -1);
        if(rottenTime == -1) {
            rottenTime = RotScheduler.plugin.getConfig().getInt("foodBestBefore");
        } else {
            rottenTime--;
        }

        data.set(rottenIn, PersistentDataType.INTEGER, rottenTime);

        if(rottenTime == 0) {
            item.setType(Material.ROTTEN_FLESH);
            meta.setLore(new ArrayList<String>());
        } else {
            try {
                List<String> itemLore = new ArrayList<String>();
                itemLore.add(ChatColor.RESET + "" + ChatColor.ITALIC + "" + ChatColor.GRAY + plugin.getConfig().getString("rottenTimeSuffix")
                        .replace("$1", String.valueOf(rottenTime)));
                meta.setLore(itemLore);
            }
            catch(NullPointerException e) {
                throw new IllegalStateException("rottenTimeSuffix in config.yml must exist.");
            }
        }
        item.setItemMeta(meta);
    }
}
