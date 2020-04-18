package io.github.lukeeff.config;

import io.github.lukeeff.ImprovedVillagers;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DefaultConfig {

    private ImprovedVillagers plugin;
    private FileConfiguration config;

    public DefaultConfig(ImprovedVillagers instance) {
        plugin = instance;
    }

    public void loadConfig() {
        createDataFolder();
        createConfigFile();
    }

    private void createDataFolder() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    private void initializeConfig(File configFile) {
        this.config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (InvalidConfigurationException | IOException e) {
            config = plugin.getConfig();
            e.printStackTrace();
        }
    }

    private void createConfigFile() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        config = new YamlConfiguration();

        if (!configFile.exists()) {
            plugin.getLogger().info(ChatColor.GREEN + "Creating new config file");
            plugin.saveDefaultConfig();
        }
        initializeConfig(configFile);
    }

    public int getMaxDiscount() {
        return config.getInt("max-discount");
    }

}
