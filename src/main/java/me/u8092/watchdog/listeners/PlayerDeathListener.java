package me.u8092.watchdog.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getPlayer();

        /*
        if (!(Objects.requireNonNull(target.getLastDamageCause()).getEntity() instanceof Player killer))
            return;

        BrawlPlayer brawlTarget = BrawlPlayerHandler.getBrawlPlayer(target);
        BrawlPlayer brawlKiller = BrawlPlayerHandler.getBrawlPlayer(killer);

        if(brawlTarget != null) {
            if(brawlTarget.getStreak() > 5) MessageUtil.redEvent(target, translatable("streak.lost", text(brawlTarget.getStreak())));

            brawlTarget.removeMytharites(2);

            brawlTarget.addDeath();
            brawlTarget.resetStreak();
            target.getInventory().clear();
        }

        if(brawlKiller != null) {
            brawlKiller.addKill();
            brawlKiller.addMytharites(2 + brawlKiller.getStreak());

            if(brawlKiller.getStreak() > 5) MessageUtil.greenEvent(target, translatable("streak.reminder", text(brawlKiller.getStreak())));
        }

         */
    }
}
