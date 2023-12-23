package net.quimmilho.lobbyprotection.events;

import net.quimmilho.lobbyprotection.LobbyProtection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteraction implements Listener {

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent e) {
        if (e.getPlayer().getLocation().getWorld().getName().equals(LobbyProtection
                .getInstance().getLobbyName()))
            e.setCancelled(true);
    }

}
