package me.u8092.watchdog.listeners;

import me.u8092.watchdog.util.MessagerUtil;
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
        // Todo: could be optimized
        Entity victim = event.getEntity();

        Map<String, String> lookFor = new HashMap<>();

        if(victim instanceof Player) {
            lookFor.put("targetPlayer", victim.getName());
            MessagerUtil.updateVariables("PLAYER_DEATH_EVENT", victim.getName(), true);
            MessagerUtil.send(((Player) victim).getPlayer(), "PLAYER_DEATH_EVENT", lookFor);

            if(!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent entityDamageByEntityEvent)) return;

            Entity killer = entityDamageByEntityEvent.getDamager();
            if(killer instanceof Player) {
                lookFor.put("player", killer.getName());
                MessagerUtil.updateVariables("PLAYER_KILLS_PLAYER_EVENT", victim.getName(), true);
                MessagerUtil.send(((Player) killer).getPlayer(), "PLAYER_KILLS_PLAYER_EVENT", lookFor);
            }
        }

        if(victim instanceof Creature) {
            if(!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent entityDamageByEntityEvent)) return;

            Entity killer = entityDamageByEntityEvent.getDamager();
            if(killer instanceof Player) {
                lookFor.put("player", killer.getName());
                MessagerUtil.updateVariables("PLAYER_KILLS_CREATURE_EVENT", victim.getName(), true);
                MessagerUtil.send(((Player) killer).getPlayer(), "PLAYER_KILLS_CREATURE_EVENT", lookFor);
            }
        }
    }
}
