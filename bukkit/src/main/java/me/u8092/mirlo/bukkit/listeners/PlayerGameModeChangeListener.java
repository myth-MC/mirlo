package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerGameModeChangeListener implements Listener {
    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Map<String, String> lookFor = new HashMap<>();

        MirloVariableHandler.update("GAMEMODE", event.getPlayer().getName(), true);

        lookFor.put("targetPlayer", event.getPlayer().getName());
        lookFor.put("gamemode", event.getNewGameMode().name());

        for (MirloMessage message : MirloMessageHandler.formatEvent(event.getPlayer().getName(), "GAMEMODE", lookFor)) {
            message.send();
        }
    }
}
