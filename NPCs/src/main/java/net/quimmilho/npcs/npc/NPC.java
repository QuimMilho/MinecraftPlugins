package net.quimmilho.npcs.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.quimmilho.npcs.NPCs;
import net.quimmilho.npcs.colors.Color;
import net.quimmilho.npcs.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class NPC {

    private static final HashMap<String, NPC> list = new HashMap<>();

    private static MinecraftServer server;
    private final ServerLevel level;

    private final Location loc;
    private final GameProfile profile;
    private final String cmd, name, texture, signature, color;

    public NPC(String name, String color, Location loc, String texture, String signature,
               String cmd) {
        this.name = name;
        this.color = color;
        this.loc = loc;
        this.texture = texture;
        this.signature = signature;
        this.cmd = cmd;

        profile = new GameProfile(UUID.randomUUID(), Color.encode(color) + this.name);
        profile.getProperties().put("textures", new Property("textures", this.texture, this.signature));

        level = ((CraftWorld) loc.getWorld()).getHandle();

        list.put(this.name, this);
    }

    private void spawn(Player p) throws NoSuchFieldException, IllegalAccessException {
        ServerPlayer sp = new ServerPlayer(server, level, profile, ClientInformation.createDefault());
        sp.absMoveTo(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        ServerPlayer cli = ((CraftPlayer) p).getHandle();
        ServerGamePacketListenerImpl packetListener = cli.connection;

        SynchedEntityData synchedEntityData = sp.getEntityData();
        synchedEntityData.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 127);

        Field field = sp.getClass().getDeclaredField("c");
        field.setAccessible(true);
        field.set(sp, packetListener);

        packetListener.send(new ClientboundPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, sp));
        packetListener.send(new ClientboundAddEntityPacket(sp));
        packetListener.send(new ClientboundSetEntityDataPacket(sp.getId(),
                synchedEntityData.getNonDefaultValues()));
        Bukkit.getScheduler().runTaskLaterAsynchronously(NPCs.getInstance(), () ->
                packetListener.send(new ClientboundPlayerInfoRemovePacket(
                Collections.singletonList(sp.getUUID()))), 40);
    }

    public void save() throws IOException {
        Config cfg = NPCs.getInstance().getPluginConfig();
        cfg.setConfig("npcs." + name + ".world", loc.getWorld().getName());
        cfg.setConfig("npcs." + name + ".loc.x", loc.getX());
        cfg.setConfig("npcs." + name + ".loc.y", loc.getY());
        cfg.setConfig("npcs." + name + ".loc.z", loc.getZ());
        cfg.setConfig("npcs." + name + ".loc.yaw", loc.getYaw());
        cfg.setConfig("npcs." + name + ".loc.pitch", loc.getPitch());
        cfg.setConfig("npcs." + name + ".color", color.toString());
        cfg.setConfig("npcs." + name + ".texture", texture);
        cfg.setConfig("npcs." + name + ".signature", signature);
        cfg.setConfig("npcs." + name + ".cmd", cmd);
        List<String> str = cfg.getConfig().getStringList("npcs.list");
        str.add(name);
        cfg.setConfig("npcs.list", str);
        cfg.save();
    }

    // STATIC METHODS

    public static void loadNPCsInfo() {
        server = ((CraftServer) NPCs.getInstance().getServer()).getServer();
    }

    public static void spawnNPC(Player player, String name)
            throws NoSuchFieldException, IllegalAccessException {
        NPC n = list.get(name);
        n.spawn(player);
    }

    public static void loadNPC(String name) {
        String color = NPCs.getInstance().getPluginConfig().getConfig()
                .getString("npcs." + name + ".color");
        double x, y, z, yaw, pitch;
        String world = NPCs.getInstance().getPluginConfig()
                .getConfig().getString("npcs." + name + ".world");
        x = NPCs.getInstance().getPluginConfig()
                .getConfig().getDouble("npcs." + name + ".loc.x");
        y = NPCs.getInstance().getPluginConfig()
                .getConfig().getDouble("npcs." + name + ".loc.y");
        z = NPCs.getInstance().getPluginConfig()
                .getConfig().getDouble("npcs." + name + ".loc.z");
        yaw = NPCs.getInstance().getPluginConfig()
                .getConfig().getDouble("npcs." + name + ".loc.yaw");
        pitch = NPCs.getInstance().getPluginConfig()
                .getConfig().getDouble("npcs." + name + ".loc.pitch");
        Location loc = new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch);
        String texture, signature, cmd;
        texture = NPCs.getInstance().getPluginConfig()
                .getConfig().getString("npcs." + name + ".texture");
        signature = NPCs.getInstance().getPluginConfig()
                .getConfig().getString("npcs." + name + ".signature");
        cmd = NPCs.getInstance().getPluginConfig()
                .getConfig().getString("npcs." + name + ".cmd");
        new NPC(name, color, loc, texture, signature, cmd);
    }

    public static Set<String> nameList() {
        return list.keySet();
    }

}
