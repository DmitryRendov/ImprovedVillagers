package io.github.lukeeff;

import io.github.lukeeff.config.DefaultConfig;
import io.github.lukeeff.listener.InventoryListener;
import io.github.lukeeff.version.VersionPointer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ImprovedVillagers extends JavaPlugin {

    public VersionPointer versionPointer;
    private DefaultConfig config;


    @Override
    public void onEnable() {
        config = new DefaultConfig(this);
        config.loadConfig();
        versionPointer = new VersionPointer(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabled IVillagers v0.3.0");
        Bukkit.getPluginManager().registerEvents(new InventoryListener(config.getMaxDiscount()), this);
    }

    @Override
    public void onDisable() {

    }

}
