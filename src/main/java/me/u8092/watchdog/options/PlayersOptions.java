package me.u8092.watchdog.options;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayersOptions implements ConfigurationSerializable {
    private boolean streak;
    private boolean streakResetOnDeath;

    private boolean killEvent;
    private boolean deathEvent;

    private int killEventMytharitesAmount;
    private boolean killEventPlayers;
    private boolean killEventHostileMobs;
    private boolean killEventPeacefulMobs;
    private int killEventStreakMultiplier;

    private int deathEventMytharitesAmount;
    private boolean deathEventPlayers;
    private boolean deathEventOthers;
    private int deathEventStreakMultiplier;

    public PlayersOptions(boolean streak,
                          boolean streakResetOnDeath,
                          boolean killEvent,
                          boolean deathEvent,
                          int killEventMytharitesAmount,
                          boolean killEventPlayers,
                          boolean killEventHostileMobs,
                          boolean killEventPeacefulMobs,
                          int killEventStreakMultiplier,
                          int deathEventMytharitesAmount,
                          boolean deathEventPlayers,
                          boolean deathEventOthers,
                          int deathEventStreakMultiplier) {
        this.streak = streak;
        this.streakResetOnDeath = streakResetOnDeath;
        this.killEvent = killEvent;
        this.deathEvent = deathEvent;

        this.killEventMytharitesAmount = killEventMytharitesAmount;
        this.killEventPlayers = killEventPlayers;
        this.killEventHostileMobs = killEventHostileMobs;
        this.killEventPeacefulMobs = killEventPeacefulMobs;
        this.killEventStreakMultiplier = killEventStreakMultiplier;

        this.deathEventMytharitesAmount = deathEventMytharitesAmount;
        this.deathEventPlayers = deathEventPlayers;
        this.deathEventOthers = deathEventOthers;
        this.deathEventStreakMultiplier = deathEventStreakMultiplier;
    }

    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("players.streak.trigger", this.streak);
        data.put("players.streak.resetOnDeath", this.streakResetOnDeath);
        data.put("players.killEvent.trigger", this.killEvent);
        data.put("players.deathEvent.trigger", this.deathEvent);

        data.put("players.killEvent.mytharitesAmount", this.killEventMytharitesAmount);
        data.put("players.killEvent.players", this.killEventPlayers);
        data.put("players.killEvent.hostileMobs", this.killEventHostileMobs);
        data.put("players.killEvent.peacefulMobs", this.killEventPeacefulMobs);
        data.put("players.killEvent.streakMultiplier", this.killEventStreakMultiplier);

        data.put("players.deathEvent.mytharitesAmount", this.deathEventMytharitesAmount);
        data.put("players.deathEvent.players", this.deathEventPlayers);
        data.put("players.deathEvent.others", this.deathEventOthers);
        data.put("players.deathEvent.streakMultiplier", this.deathEventStreakMultiplier);

        return data;
    }

    public static PlayersOptions deserialize(Map<String, Object> args) {
        return new PlayersOptions(
                (boolean) args.get("players.streak.trigger"),
                (boolean) args.get("players.streak.resetOnDeath"),
                (boolean) args.get("players.killEvent.trigger"),
                (boolean) args.get("players.deathEvent.trigger"),

                (int) args.get("players.killEvent.mytharitesAmount"),
                (boolean) args.get("players.killEvent.players"),
                (boolean) args.get("players.killEvent.hostileMobs"),
                (boolean) args.get("players.killEvent.peacefulMobs"),
                (int) args.get("players.killEvent.streakMultiplier"),

                (int) args.get("players.deathEvent.mytharitesAmount"),
                (boolean) args.get("players.deathEvent.players"),
                (boolean) args.get("players.deathEvent.others"),
                (int) args.get("players.deathEvent.streakMultiplier")
        );
    }
}
