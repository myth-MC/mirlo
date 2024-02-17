package me.u8092.watchdog.options;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SessionsOptions implements ConfigurationSerializable {
    private boolean friendlyFire;

    public SessionsOptions(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("sessions.friendlyFire", this.friendlyFire);

        return data;
    }

    public static SessionsOptions deserialize(Map<String, Object> args) {
        return new SessionsOptions(
                (boolean) args.get("sessions.friendlyFire")
        );
    }
}
