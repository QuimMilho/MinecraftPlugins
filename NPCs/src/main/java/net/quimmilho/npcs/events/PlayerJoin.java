package net.quimmilho.npcs.events;

import net.quimmilho.npcs.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) throws NoSuchFieldException, IllegalAccessException {
        Player p = e.getPlayer();
        for (String name : NPC.nameList()) {
            NPC.spawnNPC(p, name);
        }
    }

}
