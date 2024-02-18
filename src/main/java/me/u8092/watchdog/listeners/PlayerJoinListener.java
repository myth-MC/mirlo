package me.u8092.watchdog.listeners;

import me.u8092.watchdog.Main;
import me.u8092.watchdog.variables.BooleanVariable;
import me.u8092.watchdog.variables.IntVariable;
import me.u8092.watchdog.variables.VariableHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoinListener implements Listener {
    private final FileConfiguration configuration = Main.getInstance().getConfig();
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        registerPlayerVariables(event.getPlayer().getName());
    }

    private void registerPlayerVariables(String playerName) {
        for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
            if(configuration.getString("variables." + variable + ".scope").equals("player")) {
                if(configuration.getString("variables." + variable + ".type").equals("int")) {
                    VariableHandler.addIntVariable(new IntVariable(
                            variable,
                            configuration.getInt("variables." + variable + ".default"),
                            configuration.getStringList("variables." + variable + ".increase"),
                            configuration.getStringList("variables." + variable + ".decrease"),
                            configuration.getStringList("variables." + variable + ".reset"),
                            playerName
                    ));
                }

                if(configuration.getString("variables." + variable + ".type").equals("boolean")) {
                    VariableHandler.addBooleanVariable(new BooleanVariable(
                            variable,
                            configuration.getBoolean("variables." + variable + ".default"),
                            playerName
                    ));
                }
            }
        }
    }
}
