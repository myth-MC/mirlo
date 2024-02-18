package me.u8092.watchdog.commands;

import me.u8092.watchdog.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;
        if(!player.hasPermission("watchdog.reload")) return false;

        Main.getInstance().reloadConfig();
        if(Main.getInstance().getConfig().getBoolean("debug")) Main.getInstance().getLogger().info("config.yml has been reloaded");

        return true;
    }
}
