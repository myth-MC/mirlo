package me.u8092.mirlo.bukkit.commands;

import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.bukkit.MirloBukkit;
import me.u8092.mirlo.bukkit.util.DebugUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("mirlo.reload")) return false;

        Mirlo.get().reload();

        for(Player player : Bukkit.getOnlinePlayers()) {
            reloadVariables(player.getName());
        }

        reloadVariables("global");

        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug")) DebugUtil.info("config.yml has been reloaded");

        return true;
    }

    private void reloadVariables(String varOwner) {
        /*
        for(BooleanVariable booleanVariable : VariableHandler.getBooleanVariables(varOwner)) {
            BooleanVariable newBooleanVariable =
                    new BooleanVariable(
                            booleanVariable.getName(),
                            booleanVariable.getDefaultValue(),
                            configuration.getStringList("variables." + booleanVariable.getName() + ".true"),
                            configuration.getStringList("variables." + booleanVariable.getName() + ".false"),
                            configuration.getStringList("variables." + booleanVariable.getName() + ".switch"),
                            configuration.getStringList("variables." + booleanVariable.getName() + ".reset"),
                            varOwner
                    );

            VariableHandler.removeBooleanVariable(booleanVariable);
            VariableHandler.addBooleanVariable(newBooleanVariable);
        }

        for(CountVariable countVariable : VariableHandler.getCountVariables(varOwner)) {
            CountVariable newCountVariable =
                    new CountVariable(
                            countVariable.getName(),
                            countVariable.getDefaultValue(),
                            configuration.getStringList("variables." + countVariable.getName() + ".increase"),
                            configuration.getStringList("variables." + countVariable.getName() + ".decrease"),
                            configuration.getStringList("variables." + countVariable.getName() + ".reset"),
                            varOwner
                    );

            VariableHandler.removeCountVariable(countVariable);
            VariableHandler.addCountVariable(newCountVariable);
        }

         */
    }
}
