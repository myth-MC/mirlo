package me.u8092.watchdog.objects;

import java.util.Map;
import java.util.UUID;

public class WatchdogPlayerHandler {
    private static Map<UUID, WatchdogPlayer> watchdogPlayerMap;

    public static Map<UUID, WatchdogPlayer> getWatchdogPlayerMap() {
        return watchdogPlayerMap;
    }

    public static WatchdogPlayer getWatchdogPlayer(UUID uuid) {
        if(watchdogPlayerMap.containsKey(uuid)) return watchdogPlayerMap.get(uuid);
        return null;
    }
}
