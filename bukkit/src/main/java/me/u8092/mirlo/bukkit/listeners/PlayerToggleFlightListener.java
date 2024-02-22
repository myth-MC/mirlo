package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerToggleFlightListener implements Listener {
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Map<String, String> lookFor = new HashMap<>();

        MirloVariableHandler.update("FLIGHT", event.getPlayer().getName(), true);

        lookFor.put("targetPlayer", event.getPlayer().getName());
        lookFor.put("status", String.valueOf(event.isFlying()));

        for (MirloMessage message : MirloMessageHandler.formatEvent(event.getPlayer().getName(), "FLIGHT", lookFor)) {
            message.send();
        }
    }
}
