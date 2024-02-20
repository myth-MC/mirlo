package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.common.util.MirloMessageHandler;
import me.u8092.mirlo.common.util.MirloVariableHandler;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();

        Map<String, String> lookFor = new HashMap<>();

        if (victim instanceof Player) {
            lookFor.put("targetPlayer", victim.getName());
            MirloVariableHandler.update("PLAYER_DEATH_EVENT", victim.getName(), true);

            for (MirloMessage message : MirloMessageHandler.formatEvent(victim.getName(), "PLAYER_DEATH_EVENT", lookFor)) {
                message.send();
            }
        }

        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
            if (entityDamageByEntityEvent.getDamager() instanceof Player killer) {
                lookFor.put("player", killer.getName());

                if (victim instanceof Creature) {
                    MirloVariableHandler.update("PLAYER_KILLS_CREATURE_EVENT", victim.getName(), true);

                    for (MirloMessage message : MirloMessageHandler.formatEvent(victim.getName(), "PLAYER_KILLS_CREATURE_EVENT", lookFor)) {
                        message.send();
                    }
                }

                if (victim instanceof Player) {
                    lookFor.put("targetPlayer", victim.getName());

                    MirloVariableHandler.update("PLAYER_KILLS_PLAYER_EVENT", killer.getName(), true);

                    for (MirloMessage message : MirloMessageHandler.formatEvent(killer.getName(), "PLAYER_KILLS_PLAYER_EVENT", lookFor)) {
                        message.send();
                    }
                }
            }
        }
    }
}
