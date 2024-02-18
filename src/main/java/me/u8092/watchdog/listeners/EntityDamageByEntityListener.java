package me.u8092.watchdog.listeners;

import me.u8092.alzalibs.clans.ClansAPI;
import me.u8092.alzalibs.users.UsersAPI;
import me.u8092.watchdog.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    private final FileConfiguration configuration = Main.getInstance().getConfig();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {
            if(!configuration.getBoolean("clans.friendlyFire")) {
                String victimClan = UsersAPI.getApi().getClanName(victim.getName());
                String attackerClan = UsersAPI.getApi().getClanName(attacker.getName());

                if(victimClan != null && attackerClan != null) {
                    if(victimClan.equals(attackerClan)) event.setCancelled(true);
                }
            }
        }
    }
}
