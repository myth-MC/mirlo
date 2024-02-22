package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerExpChangeListener implements Listener {
    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Map<String, String> lookFor = new HashMap<>();

        MirloVariableHandler.update("XP_CHANGE", event.getPlayer().getName(), true);

        lookFor.put("targetPlayer", event.getPlayer().getName());
        lookFor.put("xp", String.valueOf(event.getAmount()));
        lookFor.put("totalXp", String.valueOf(event.getPlayer().getExp()));

        for (MirloMessage message : MirloMessageHandler.formatEvent(event.getPlayer().getName(), "XP_CHANGE", lookFor)) {
            message.send();
        }
    }
}
