package me.u8092.watchdog.options;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ClansOptions implements ConfigurationSerializable {
    private boolean friendlyFire;
    private boolean killEvent;
    private boolean deathEvent;

    private int killEventXpAmount;
    private boolean killEventPlayers;
    private boolean killEventHostileMobs;
    private boolean killEventPeacefulMobs;
    private int killEventForeignClanMultiplier;

    private int deathEventXpAmount;
    private boolean deathEventPlayers;
    private boolean deathEventOthers;
    private int deathEventForeignClanMultiplier;

    public ClansOptions(boolean friendlyFire,
                        boolean killEvent,
                        boolean deathEvent,
                        int killEventXpAmount,
                        boolean killEventPlayers,
                        boolean killEventHostileMobs,
                        boolean killEventPeacefulMobs,
                        int killEventForeignClanMultiplier,
                        int deathEventXpAmount,
                        boolean deathEventPlayers,
                        boolean deathEventOthers,
                        int deathEventForeignClanMultiplier) {
        this.friendlyFire = friendlyFire;
        this.killEvent = killEvent;
        this.deathEvent = deathEvent;

        this.killEventXpAmount = killEventXpAmount;
        this.killEventPlayers = killEventPlayers;
        this.killEventHostileMobs = killEventHostileMobs;
        this.killEventPeacefulMobs = killEventPeacefulMobs;
        this.killEventForeignClanMultiplier = killEventForeignClanMultiplier;

        this.deathEventXpAmount = deathEventXpAmount;
        this.deathEventPlayers = deathEventPlayers;
        this.deathEventOthers = deathEventOthers;
        this.deathEventForeignClanMultiplier = deathEventForeignClanMultiplier;
    }

    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("clans.friendlyFire", this.friendlyFire);
        data.put("clans.killEvent.trigger", this.killEvent);
        data.put("clans.deathEvent.trigger", this.deathEvent);

        data.put("clans.killEvent.xpAmount", this.killEventXpAmount);
        data.put("clans.killEvent.players", this.killEventPlayers);
        data.put("clans.killEvent.hostileMobs", this.killEventHostileMobs);
        data.put("clans.killEvent.peacefulMobs", this.killEventPeacefulMobs);
        data.put("clans.killEvent.foreignClanMultiplier", this.killEventForeignClanMultiplier);

        data.put("clans.deathEvent.xpAmount", this.deathEventXpAmount);
        data.put("clans.deathEvent.players", this.deathEventPlayers);
        data.put("clans.deathEvent.others", this.deathEventOthers);
        data.put("clans.deathEvent.foreignClanMultiplier", this.deathEventForeignClanMultiplier);

        return data;
    }

    public static ClansOptions deserialize(Map<String, Object> args) {
        return new ClansOptions(
                (boolean) args.get("clans.friendlyFire"),
                (boolean) args.get("clans.killEvent.trigger"),
                (boolean) args.get("clans.deathEvent.trigger"),

                (int) args.get("clans.killEvent.xpAmount"),
                (boolean) args.get("clans.killEvent.players"),
                (boolean) args.get("clans.killEvent.hostileMobs"),
                (boolean) args.get("clans.killEvent.peacefulMobs"),
                (int) args.get("clans.killEvent.foreignClanMultiplier"),

                (int) args.get("clans.deathEvent.xpAmount"),
                (boolean) args.get("clans.deathEvent.players"),
                (boolean) args.get("clans.deathEvent.others"),
                (int) args.get("clans.deathEvent.foreignClanMultiplier")
        );
    }

    public boolean friendlyFire() { return friendlyFire; }
}
