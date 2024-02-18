package me.u8092.mirlo.bukkit.commands;

import me.u8092.mirlo.bukkit.util.DebugUtil;
import me.u8092.mirlo.bukkit.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("mirlo.reload")) return false;

        Main.getInstance().reloadConfig();
        if(Main.getInstance().getConfig().getBoolean("debug")) DebugUtil.info("config.yml has been reloaded");

        return true;
    }
}
