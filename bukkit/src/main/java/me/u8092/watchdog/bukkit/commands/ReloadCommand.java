package me.u8092.watchdog.bukkit.commands;

import me.u8092.watchdog.bukkit.Main;
import me.u8092.watchdog.bukkit.util.DebugUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("watchdog.reload")) return false;

        Main.getInstance().reloadConfig();
        if(Main.getInstance().getConfig().getBoolean("debug")) DebugUtil.info("config.yml has been reloaded");

        return true;
    }
}
