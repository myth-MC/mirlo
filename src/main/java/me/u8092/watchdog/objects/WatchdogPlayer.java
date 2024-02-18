package me.u8092.watchdog.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WatchdogPlayer {
    private final UUID uuid;
    private int streak;

    public WatchdogPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.streak = 0;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public int getStreak() {
        return streak;
    }

    public void addToStreak() {
        streak = streak + 1;
    }

    public void resetStreak() {
        streak = 0;
    }
}
