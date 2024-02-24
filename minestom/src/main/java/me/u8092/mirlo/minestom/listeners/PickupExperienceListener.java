package me.u8092.mirlo.minestom.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.item.PickupExperienceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PickupExperienceListener {
    public static void register(final @NotNull GlobalEventHandler eventHandler) {
        eventHandler.addListener(PickupExperienceEvent.class, event -> {
            Map<String, String> lookFor = new HashMap<>();

            MirloVariableHandler.update("XP_CHANGE", event.getPlayer().getUsername(), true);

            lookFor.put("targetPlayer", event.getPlayer().getUsername());
            lookFor.put("xp", String.valueOf(event.getExperienceCount()));
            lookFor.put("totalXp", String.valueOf(event.getPlayer().getExp()));

            for (MirloMessage message : MirloMessageHandler.formatEvent(event.getPlayer().getUsername(), "XP_CHANGE", lookFor)) {
                message.send();
            }
        });
    }
}
