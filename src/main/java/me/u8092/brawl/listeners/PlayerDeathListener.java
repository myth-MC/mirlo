package me.u8092.brawl.listeners;

import me.u8092.alzalibs.util.MessageUtil;
import me.u8092.brawl.match.BrawlPlayer;
import me.u8092.brawl.match.BrawlPlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getPlayer();
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
    }
}
