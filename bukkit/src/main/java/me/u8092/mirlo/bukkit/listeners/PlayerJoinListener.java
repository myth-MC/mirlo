package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.bukkit.util.DebugUtil;
import me.u8092.mirlo.common.variables.BooleanVariable;
import me.u8092.mirlo.common.variables.CountVariable;
import me.u8092.mirlo.common.variables.VariableHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.simpleyaml.configuration.ConfigurationSection;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        registerPlayerVariables(event.getPlayer().getName());
    }

    private void registerPlayerVariables(String playerName) {
        ConfigurationSection section = Mirlo.get().getConfig().getVariables().getSection();
        for(String variable : section.getKeys(false)) {
            if(section.getString(variable + ".scope").equals("player")) {
                if(section.getString(variable + ".type").equals("count")) {
                    VariableHandler.addCountVariable(new CountVariable(
                            variable,
                            section.getInt(variable + ".default"),
                            section.getStringList(variable + ".increase"),
                            section.getStringList(variable + ".decrease"),
                            section.getStringList(variable + ".reset"),
                            playerName
                    ));

                    if(Mirlo.get().getConfig().getSettings().isDebug()) DebugUtil.info("Registered new CountVariable '" + variable + "' for " + playerName);
                }

                if(section.getString(variable + ".type").equals("player")) {
                    VariableHandler.addBooleanVariable(new BooleanVariable(
                            variable,
                            section.getBoolean(variable + ".default"),
                            section.getStringList(variable + ".true"),
                            section.getStringList(variable + ".false"),
                            section.getStringList(variable + ".switch"),
                            section.getStringList(variable + ".reset"),
                            playerName
                    ));

                    if(Mirlo.get().getConfig().getSettings().isDebug()) DebugUtil.info("Registered new BooleanVariable '" + variable + "' for " + playerName);
                }
            }
        }
    }
}
