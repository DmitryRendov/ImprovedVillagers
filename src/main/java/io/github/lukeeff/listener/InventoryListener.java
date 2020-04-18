package io.github.lukeeff.listener;

import io.github.lukeeff.version.VersionHandler;
import io.github.lukeeff.version.VersionPointer;
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
            versionHandler.setMaxDiscount(maxDiscount, villager);

        }
    }
}
