package me.u8092.mirlo.minestom.listeners;

import me.u8092.mirlo.api.Mirlo;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerSpawnListener {
    public static void register(final @NotNull GlobalEventHandler eventHandler) {
        eventHandler.addListener(PlayerSpawnEvent.class, event -> Mirlo.get().getVariableManager().initialize("player", event.getPlayer().getUsername()));
    }
}
