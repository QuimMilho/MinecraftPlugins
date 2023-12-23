package net.quimmilho.lobbyprotection.events;

import net.quimmilho.lobbyprotection.LobbyProtection;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    private final int maxX, maxY, maxZ, minX, minY, minZ;

    public PlayerMove() {
        minX = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("boundaries.minLimit.x");
        minY = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("boundaries.minLimit.y");
        minZ = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("boundaries.minLimit.z");
        maxX = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("boundaries.maxLimit.x");
        maxY = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("boundaries.maxLimit.y");
        maxZ = LobbyProtection.getInstance().getYamlConfig().getConfig()
                .getInt("boundaries.maxLimit.z");
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        Location l = e.getTo();
        if (l.getWorld().getName().equals(LobbyProtection.getInstance().getLobbyName()))
            if (checkPos(l))
                e.setCancelled(true);
    }

    private boolean checkPos(Location l) {
        return l.getBlockX() < minX || l.getBlockX() > maxX || l.getBlockY() < minY ||
                l.getBlockY() > maxY || l.getBlockZ() < minZ || l.getBlockZ() > maxZ;
    }

}
