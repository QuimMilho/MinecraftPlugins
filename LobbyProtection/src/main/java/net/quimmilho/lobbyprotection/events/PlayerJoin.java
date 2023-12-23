package net.quimmilho.lobbyprotection.events;

import net.quimmilho.lobbyprotection.LobbyProtection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final Location loc;

    public PlayerJoin() {
        int x = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("joinSpawnPoint.loc.x");
        int y = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("joinSpawnPoint.loc.y");
        int z = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("joinSpawnPoint.loc.z");
        int yaw = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("joinSpawnPoint.loc.yaw");
        loc = new Location(Bukkit.getWorld(LobbyProtection.getInstance().getLobbyName()),
                x + 0.5f, y + 0.5f, z + 0.5f, yaw, 0);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        e.getPlayer().teleport(loc);
    }

}
