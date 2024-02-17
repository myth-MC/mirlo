package me.u8092.brawl.match;

import me.u8092.brawl.events.BrawlPlayerCreateEvent;
import me.u8092.brawl.events.BrawlPlayerDeleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class BrawlPlayerHandler {
    private static List<BrawlPlayer> brawlPlayers;

    public static List<BrawlPlayer> getBrawlPlayers() { return brawlPlayers; }

    public static BrawlPlayer getBrawlPlayer(Player player) {
        for(BrawlPlayer brawlPlayer : brawlPlayers) {
            if(brawlPlayer.getPlayerName().equals(player.getName())) {
                return brawlPlayer;
            }
        }

        return null;
    }

    public static BrawlPlayer getBrawlPlayer(String username) {
        for(BrawlPlayer brawlPlayer : brawlPlayers) {
            if(brawlPlayer.getPlayerName().equals(username)) {
                return brawlPlayer;
            }
        }

        return null;
    }

    public static boolean createBrawlPlayer(BrawlPlayer brawlPlayer) {
        if(brawlPlayers.contains(brawlPlayer)) return false;

        Bukkit.getPluginManager().callEvent(new BrawlPlayerCreateEvent(brawlPlayer));
        brawlPlayers.add(brawlPlayer);
        return true;
    }

    public static boolean deleteBrawlPlayer(BrawlPlayer brawlPlayer) {
        Bukkit.getPluginManager().callEvent(new BrawlPlayerDeleteEvent(brawlPlayer));
        return brawlPlayers.removeIf(obj -> (obj == brawlPlayer));
    }
}
