package me.u8092.mirlo.minestom.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityDeathListener {
    public static void register(final @NotNull GlobalEventHandler eventHandler) {
        eventHandler.addListener(EntityDeathEvent.class, event -> {
            if(!(event.getEntity() instanceof LivingEntity victim)) return;

            Map<String, String> lookFor = new HashMap<>();

            if (victim instanceof Player) {
                lookFor.put("targetPlayer", ((Player) victim).getName().toString());
                MirloVariableHandler.update("DEATH", ((Player) victim).getName().toString(), true);

                for (MirloMessage message : MirloMessageHandler.formatEvent(((Player) victim).getName().toString(), "DEATH", lookFor)) {
                    message.send();
                }

            }

            if (Objects.requireNonNull(victim.getLastDamageSource()).getSource() instanceof Player killer) {
                if (victim instanceof EntityCreature) {
                    MirloVariableHandler.update("PLAYER_KILLS_CREATURE", killer.getUsername(), true);

                    for (MirloMessage message : MirloMessageHandler.formatEvent(killer.getUsername(), "PLAYER_KILLS_CREATURE", lookFor)) {
                        message.send();
                    }
                }

                if (victim instanceof Player) {
                    lookFor.put("targetPlayer", ((Player) victim).getUsername());

                    MirloVariableHandler.update("PLAYER_KILLS_PLAYER", killer.getUsername(), true);

                    for (MirloMessage message : MirloMessageHandler.formatEvent(killer.getUsername(), "PLAYER_KILLS_PLAYER", lookFor)) {
                        message.send();
                    }
                }
            }
        });
    }
}
