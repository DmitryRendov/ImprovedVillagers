package io.github.lukeeff.listener;

import io.github.lukeeff.version.VersionHandler;
import io.github.lukeeff.version.VersionPointer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Merchant;

public class InventoryListener implements Listener {

    private int maxDiscount;
    private VersionHandler versionHandler;

    public InventoryListener(int maxDiscount) {
        this.maxDiscount = maxDiscount;
        versionHandler = VersionPointer.versionHandler;
    }

    @EventHandler
    public void inventoryOpenListener(InventoryOpenEvent event) {

        if (event.getInventory().getType().equals(InventoryType.MERCHANT) && event.getInventory().getHolder() instanceof Merchant) {
            Villager villager = (Villager) event.getInventory().getHolder();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[ImprovedVillagers] " + ChatColor.AQUA +
                    "User " + event.getPlayer().getName() + " is checking the villager at (" + (int) villager.getLocation().getX() +
                    ", " + (int) villager.getLocation().getY() +
                    ", " + (int) villager.getLocation().getX() + ")");
            versionHandler.setMaxDiscount(maxDiscount, villager);
        }
    }
}
