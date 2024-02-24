package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.bukkit.MirloBukkit;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerLocaleChangeListener implements Listener {
    @EventHandler
    public void onPlayerLocaleChange(PlayerLocaleChangeEvent event) {
        Bukkit.getScheduler().runTaskLater(MirloBukkit.INSTANCE.getPlugin(), () -> {
            Map<String, String> lookFor = new HashMap<>();

            MirloVariableHandler.update("LOCALE", event.getPlayer().getName(), true);

            lookFor.put("targetPlayer", event.getPlayer().getName());
            lookFor.put("locale", event.getPlayer().locale().toLanguageTag());

            for (MirloMessage message : MirloMessageHandler.formatEvent(event.getPlayer().getName(), "LOCALE", lookFor)) {
                message.send();
            }
        // Wait 1 sec before triggering LOCALE event
        }, 20L);
    }
}
