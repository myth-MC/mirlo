package me.u8092.mirlo.bukkit.listeners;

import me.u8092.mirlo.bukkit.MirloBukkit;
import me.u8092.mirlo.bukkit.util.DebugUtil;
import me.u8092.mirlo.common.variables.BooleanVariable;
import me.u8092.mirlo.common.variables.CountVariable;
import me.u8092.mirlo.common.variables.VariableHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final FileConfiguration configuration = MirloBukkit.INSTANCE.getPlugin().getConfig();
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        registerPlayerVariables(event.getPlayer().getName());
    }

    private void registerPlayerVariables(String playerName) {
        for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
            if(configuration.getString("variables." + variable + ".scope").equals("player")) {
                if(configuration.getString("variables." + variable + ".type").equals("count")) {
                    VariableHandler.addCountVariable(new CountVariable(
                            variable,
                            configuration.getInt("variables." + variable + ".default"),
                            configuration.getStringList("variables." + variable + ".increase"),
                            configuration.getStringList("variables." + variable + ".decrease"),
                            configuration.getStringList("variables." + variable + ".reset"),
                            playerName
                    ));

                    if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug")) DebugUtil.info("Registered new CountVariable '" + variable + "' for " + playerName);
                }

                if(configuration.getString("variables." + variable + ".type").equals("boolean")) {
                    VariableHandler.addBooleanVariable(new BooleanVariable(
                            variable,
                            configuration.getBoolean("variables." + variable + ".default"),
                            configuration.getStringList("variables." + variable + ".true"),
                            configuration.getStringList("variables." + variable + ".false"),
                            configuration.getStringList("variables." + variable + ".switch"),
                            configuration.getStringList("variables." + variable + ".reset"),
                            playerName
                    ));

                    if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug")) DebugUtil.info("Registered new BooleanVariable '" + variable + "' for " + playerName);
                }
            }
        }
    }
}
