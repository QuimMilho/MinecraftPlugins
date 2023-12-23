package net.quimmilho.npcs;

import net.quimmilho.npcs.commands.CmdCreateNPC;
import net.quimmilho.npcs.config.Config;
import net.quimmilho.npcs.events.PlayerJoin;
import net.quimmilho.npcs.npc.NPC;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;

public final class NPCs extends JavaPlugin {

    private static NPCs instance;

    private Config config;

    @Override
    public void onEnable() {
        instance = this;
        try {
            config = new Config();
        } catch (IOException | InvalidConfigurationException e) {
            setEnabled(false);
            throw new RuntimeException(e);
        }
        NPC.loadNPCsInfo();

        ArrayList<String> npcs = (ArrayList<String>) config.getConfig().getStringList("npcs.list");
        for (String s : npcs) {
            NPC.loadNPC(s);
        }

        registerEvents();
        registerCommands();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(),
                this);
    }

    private void registerCommands() {
        getServer().getCommandMap().register("createnpc", getName(), new CmdCreateNPC());
    }

    @Override
    public void onDisable() {}

    public static NPCs getInstance() {
        return instance;
    }

    public Config getPluginConfig() {
        return config;
    }

}
