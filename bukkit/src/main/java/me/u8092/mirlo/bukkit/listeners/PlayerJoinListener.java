package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.Mirlo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Mirlo.get().getVariableManager().initialize("player", event.getPlayer().getName());
    }
}
