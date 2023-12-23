package net.quimmilho.npcs.config;

import net.quimmilho.npcs.NPCs;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Config {

    private final YamlConfiguration config;
    private final File f;

    public Config() throws IOException, InvalidConfigurationException {
        config = new YamlConfiguration();
        f = new File(NPCs.getInstance().getDataFolder(), "config.yml");
        if (!f.exists()) {
            if (!NPCs.getInstance().getDataFolder().mkdirs()) {
                NPCs.getInstance().getLogger().warning("Couldn't create config folder!");
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
        config.addDefault("npcs.list", new ArrayList<String>());
        config.options().copyDefaults(true);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(String name, Object value) {
        config.set(name, value);
    }

    public void save() throws IOException {
        config.save(f);
    }

}
