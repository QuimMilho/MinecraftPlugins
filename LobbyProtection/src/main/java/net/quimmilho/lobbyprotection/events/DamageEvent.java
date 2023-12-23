package net.quimmilho.lobbyprotection.events;

import net.quimmilho.lobbyprotection.LobbyProtection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void playerDamage(EntityDamageEvent e) {
        if (e.getEntity().getLocation().getWorld().getName().equals(LobbyProtection
                .getInstance().getLobbyName()))
            e.setCancelled(true);
    }

}
