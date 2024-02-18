package me.u8092.mirlo.bukkit.util;

import me.u8092.mirlo.bukkit.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DebugUtil {
    public static void info(String message) {
        Main.getInstance().getLogger().info(message);
        String PREFIX = Main.getInstance().getConfig().getString("debug-prefix");
        if(PREFIX == null) PREFIX = "[MirloDebug]";

        if(Main.isPaper()) {
            Component playerMessage = Component.text(PREFIX + " ", NamedTextColor.DARK_GREEN)
                    .append(Component.text(message, NamedTextColor.GRAY));

            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasPermission("mirlo.debug.receive")) player.sendMessage(playerMessage);
            }
            return;
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasPermission("mirlo.debug.receive")) player.sendMessage(PREFIX + " " + message);
        }
    }
}
