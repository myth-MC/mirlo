package me.u8092.mirlo.minestom.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerStopFlyingEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayerStopFlyingListener {
    public static void register(final @NotNull GlobalEventHandler eventHandler) {
        eventHandler.addListener(PlayerStopFlyingEvent.class, event -> {
            Map<String, String> lookFor = new HashMap<>();

            MirloVariableHandler.update("FLIGHT", event.getPlayer().getUsername(), true);

            lookFor.put("targetPlayer", event.getPlayer().getUsername());
            lookFor.put("status", String.valueOf(event.getPlayer().isFlying()));

            for (MirloMessage message : MirloMessageHandler.formatEvent(event.getPlayer().getUsername(), "FLIGHT", lookFor)) {
                message.send();
            }
        });
    }
}
