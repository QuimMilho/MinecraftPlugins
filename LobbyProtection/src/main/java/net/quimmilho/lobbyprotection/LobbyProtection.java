package net.quimmilho.lobbyprotection;

import net.quimmilho.lobbyprotection.configuration.Config;
import net.quimmilho.lobbyprotection.events.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class LobbyProtection extends JavaPlugin {

    private static LobbyProtection instance;
    private Config config;
    private String lobbyName;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Loading Configuration!");
        try {
            config = new Config();
            lobbyName = config.getConfig().getString("lobby.worldName");
        } catch (IOException | InvalidConfigurationException e) {
            this.setEnabled(false);
            throw new RuntimeException(e);
        }
        getLogger().info("Registering Protections!");
        registerEvents();
        getLogger().info("Protections Registered!");
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInteraction(), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        if (config.getConfig().getBoolean("joinSpawnPoint.enable"))
            getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        if (config.getConfig().getBoolean("respawnPoint.enable"))
            getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        if (config.getConfig().getBoolean("boundaries.enable"))
            getServer().getPluginManager().registerEvents(new PlayerMove(), this);
    }

    @Override
    public void onDisable() {}

    // GETTERS AND SETTERS

    public static LobbyProtection getInstance() {
        return instance;
    }

    public Config getYamlConfig() {
        return config;
    }

    public String getLobbyName() {
        return lobbyName;
    }

}
