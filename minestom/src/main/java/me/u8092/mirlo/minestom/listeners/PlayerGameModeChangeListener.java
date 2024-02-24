package me.u8092.mirlo.minestom.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerGameModeChangeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayerGameModeChangeListener {
    public static void register(final @NotNull GlobalEventHandler eventHandler) {
        eventHandler.addListener(PlayerGameModeChangeEvent.class, event -> {
            Map<String, String> lookFor = new HashMap<>();

            MirloVariableHandler.update("GAMEMODE", event.getPlayer().getUsername(), true);

            lookFor.put("targetPlayer", event.getPlayer().getUsername());
            lookFor.put("gamemode", event.getNewGameMode().name());

            for (MirloMessage message : MirloMessageHandler.formatEvent(event.getPlayer().getUsername(), "GAMEMODE", lookFor)) {
                message.send();
            }
        });
    }
}
