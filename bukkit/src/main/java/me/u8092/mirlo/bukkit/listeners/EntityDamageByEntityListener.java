package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.bukkit.MirloBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    private final FileConfiguration configuration = MirloBukkit.INSTANCE.getPlugin().getConfig();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {

        }
    }
}
