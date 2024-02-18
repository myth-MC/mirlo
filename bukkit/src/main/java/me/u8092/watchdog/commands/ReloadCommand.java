package me.u8092.watchdog.commands;

import me.u8092.watchdog.Main;
import me.u8092.watchdog.util.DebugUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("watchdog.reload")) return false;

        Main.getInstance().reloadConfig();
        if(Main.getInstance().getConfig().getBoolean("debug")) DebugUtil.info("config.yml has been reloaded");

        return true;
    }
}
