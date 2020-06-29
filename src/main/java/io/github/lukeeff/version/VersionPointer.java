package io.github.lukeeff.version;

import io.github.lukeeff.ImprovedVillagers;
import io.github.lukeeff.version.v1_15.Handler_1_15_R1;
import io.github.lukeeff.version.v1_15.Handler_1_15_R2;
import io.github.lukeeff.version.v1_16.Handler_1_16_R1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class VersionPointer {

    //Main class instance
    ImprovedVillagers plugin;
    public static VersionHandler versionHandler;

    //Default constructor
    public VersionPointer(ImprovedVillagers instance) {
        plugin = instance;
        handleVersion();
    }

    /**
     * Handles the version of the server to ensure the correct NMS version is ran
     */
    public void handleVersion() {
        final String version = getServerVersion();
        switch (version) {
            case "v1_15_R1":
                versionHandler = new Handler_1_15_R1();
                break;
            case "v1_15_R2":
                versionHandler = new Handler_1_15_R2();
                break;
            case "v1_16_R1":
                versionHandler = new Handler_1_16_R1();
                break;
            default:
                disablePlugin();
        }
    }

    /**
     * Called when an incompatible version of the game is coupled with this plugin and will disable the plugin.
     */
    private void disablePlugin() {
        final String incompatibleVersion = ChatColor.RED + "This version of IVillagers is incompatible with " +
                "your server build. Please visit the resource on SpigotMC to see a list of compatible versions.";
        Bukkit.getConsoleSender().sendMessage(incompatibleVersion);
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    /**
     * Gets the version of the server as the following "vX_Y_RZ" with x y z being version numbers. For example,
     * version 1.15.1 would return "v1_15_R1"
     *
     * @return the version of the server.
     */
    private String getServerVersion() {
        final String version = Bukkit.getServer().getClass().getPackage().getName();
        return version.substring(version.lastIndexOf('.') + 1);
    }

}
