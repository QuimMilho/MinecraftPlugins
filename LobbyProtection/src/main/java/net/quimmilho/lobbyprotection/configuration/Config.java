package net.quimmilho.lobbyprotection.configuration;

import net.quimmilho.lobbyprotection.LobbyProtection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private YamlConfiguration config;

    public Config() throws IOException, InvalidConfigurationException {
        config = new YamlConfiguration();
        File f = new File(LobbyProtection.getInstance().getDataFolder(), "config.yml");
        if (!f.exists()) {
            if (!LobbyProtection.getInstance().getDataFolder().mkdirs()) {
                LobbyProtection.getInstance().getLogger().warning("Couldn't create config folder!");
            }
            if (!f.createNewFile()) {
                loadDefault();
                return;
            }
        }
        loadDefault();
        config.load(f);
        config.save(f);
    }

    private void loadDefault() {
        config.addDefault("lobby.worldName", "lobby");
        config.addDefault("joinSpawnPoint.enable", true);
        config.addDefault("joinSpawnPoint.loc.x", 0);
        config.addDefault("joinSpawnPoint.loc.y", 100);
        config.addDefault("joinSpawnPoint.loc.z", 0);
        config.addDefault("joinSpawnPoint.loc.yaw", 0);
        config.addDefault("respawnPoint.enable", false);
        config.addDefault("respawnPoint.loc.x", 0);
        config.addDefault("respawnPoint.loc.y", 100);
        config.addDefault("respawnPoint.loc.z", 0);
        config.addDefault("respawnPoint.loc.yaw", 0);
        config.addDefault("boundaries.enable", false);
        config.addDefault("boundaries.minLimit.x", -100);
        config.addDefault("boundaries.minLimit.y", 0);
        config.addDefault("boundaries.minLimit.z", -100);
        config.addDefault("boundaries.maxLimit.x", 100);
        config.addDefault("boundaries.maxLimit.y", 100);
        config.addDefault("boundaries.maxLimit.z", 100);
        config.options().copyDefaults(true);
    }

    //GETTERS AND SETTERS

    public YamlConfiguration getConfig() {
        return config;
    }

}
