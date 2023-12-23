package net.quimmilho.npcs.commands;

import net.kyori.adventure.text.format.NamedTextColor;
import net.quimmilho.npcs.colors.Color;
import net.quimmilho.npcs.npc.NPC;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CmdCreateNPC extends BukkitCommand {

    private static final String texture = "ewogICJ0aW1lc3RhbXAiIDogMTcwMzM0NTI0MTY2MSwKICAicHJvZmlsZUlkIiA6ICIxNDU1MDNhNDRjZmI0NzcwYmM3NWNjMTRjYjUwMDE4NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJMaWtlbHlFcmljIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ViOGI1ZTc1OWI0YzdlM2QyYzdjZGViNTFjYTNkNWNmODBlZjgwNWQ0ZTY3NGZhMGI4NmY1ODU0NGZlNzE0ODYiCiAgICB9CiAgfQp9";
    private static final String signature = "rRclExr3sxiOPXdVfZi6zJlfx5bRVH0Jwj56nUprB0laUvnJmxDG/19LhfWUbDUQtRPIUzh6l7hS+s18cKSOZRBylTxkqSrSA8QXHqpt4xom6XFeTD0w3jKSxbyCG/0Gujw9SGrkB4TF7gF7TASFmI64pl0bP4C8xR11S4AxJNMXiFPrZ/ajaI4jkifhOQ26q6NnVHE5k72FXLytNuhIw4pnorms5L8+6Eg+dgRjrpZn/YxuPtI7enxV9lhYzIsw9xF6mBtElHR51pS5L8dWfHUGMGRNdxLDpAQD4/VNTchPCu9ZfuaT9jbAO9sD0bBtssRg1M4JlK9e7jokBTPwUCWv1UEaeQnGdkt1HvOZAdGF5Ljk2wNyGul2zJ88cLDrOO6d1Rm1laybZQma6aXr0upoz+XMPty63XZZMqDcDRraOoEla+vMBnzCmB+6F/ry6s9IGS2LD2VBIOc+jFCixyeMvADi4VBBg6XBXN3pZi+xdmMPtB3dZcdZ4WH5a4P9XLWTD+Q4UJwrMzUwdGnOZpQ9q64+sYxCeBxeMANXHq4hoAg5t98slJfItSZ4S96CSrhfSQxTwI1cVRbqy5tjhRM/pvVnNe7uYx3Fqnv0RxPoBc3EP7YIVynlevKgaWMe4LhFF+mKfeQGmv4Av1KmPqxweJ9okIX1hhAU/dPyoeo=";

    public CmdCreateNPC() {
        super("createnpc");
    }

    public @NotNull List<String> tabComplete(@NotNull CommandSender commandSender,
            @NotNull String s, @NotNull String[] strings) {
        ArrayList<String> list = new ArrayList<>();
        if (commandSender.isOp()) {
            if (strings.length == 1) {
                list.add("<name>");
            } else if (strings.length == 2) {
                String[] args = strings[1].split(",");
                String before = "";
                for (int i = 0; i < args.length - 1; i++) {
                    before = before.concat(args[i] + ",");
                }
                for (Color c : Color.values()) {
                    if (c.toString().startsWith(args[args.length - 1].toUpperCase())) {
                        list.add(before + c);
                    }
                }
            } else if (strings.length >= 3) {
                list.add("<command>");
                return list;
            }
            return list;
        }
        return list;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.isOp()) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(NamedTextColor.RED + "You don't have permission!");
                return true;
            }
            if (strings.length < 3) {
                commandSender.sendMessage(NamedTextColor.RED + "Usage: /createnpc <name> <color>" +
                        " <cmd1> [...cmdN]");
                return true;
            }
            String name, color, cmd = "";
            Location loc;
            name = strings[0];
            loc = ((Player) commandSender).getLocation();
            color = strings[1];
            for (int i = 2; i < strings.length; i++) {
                cmd = cmd.concat(i == 2 ? strings[2] : " " + strings[i]);
            }
            try {
                (new NPC(name, color, loc, texture, signature, cmd)).save();
            } catch (IOException e) {
                commandSender.sendMessage(NamedTextColor.RED + "An error occurred while registering!");
                throw new RuntimeException(e);
            }
            commandSender.sendMessage(NamedTextColor.GREEN + "NPC registered!");
        } else {
            commandSender.sendMessage(NamedTextColor.RED + "You don't have permission!");
        }
        return true;
    }
}
