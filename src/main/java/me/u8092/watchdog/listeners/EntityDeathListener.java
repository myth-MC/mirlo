package me.u8092.watchdog.listeners;

import me.u8092.watchdog.Main;
import me.u8092.watchdog.variables.BooleanVariable;
import me.u8092.watchdog.variables.CountVariable;
import me.u8092.watchdog.variables.VariableHandler;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.u8092.watchdog.util.MessagerUtil.byteArray;

public class EntityDeathListener implements Listener {
    private final FileConfiguration configuration = Main.getInstance().getConfig();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();

        Map<String, String> lookFor = new HashMap<>();

        if(victim instanceof Player) {
            lookFor.put("targetPlayer", victim.getName());
            send(((Player) victim).getPlayer(), "PLAYER_DEATH_EVENT", lookFor);

            if(!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent entityDamageByEntityEvent)) return;

            Entity killer = entityDamageByEntityEvent.getDamager();
        }
    }

    private void send(Player player, String event, Map<String, String> replace) {
        List<String> channelsToSendMessage = new ArrayList<>();
        String joinedArgs = "";

        for(String channel : configuration.getConfigurationSection("channels").getKeys(false)) {
            for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                List<String> fullEvent = List.of(unformattedEvent.split(","));
                if(fullEvent.contains(event)) channelsToSendMessage.add(channel);
            }
        }

        // Replace variables
        for(String channel : channelsToSendMessage) {
            for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
                // Global variable
                if(configuration.getString("variables." + variable + ".scope").equals("global")) {
                    for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                        List<String> fullEvent = List.of(unformattedEvent.split(","));

                        if(fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                            List<BooleanVariable> booleanVariables = VariableHandler.getBooleanVariables();
                            List<CountVariable> countVariables = VariableHandler.getCountVariables();

                            joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                            for(BooleanVariable booleanVariable : booleanVariables) {
                                if(!booleanVariable.getOwner().equals("global")) continue;
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(booleanVariable.getValue()));
                            }

                            for(CountVariable countVariable : countVariables) {
                                if(!countVariable.getOwner().equals("global")) continue;
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(countVariable.getValue()));
                            }
                        }
                    }
                }

                // Player variable
                if(configuration.getString("variables." + variable + ".scope").equals("player")) {
                    for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                        List<String> fullEvent = List.of(unformattedEvent.split(","));

                        if(fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                            List<BooleanVariable> booleanVariables = VariableHandler.getBooleanVariables();
                            List<CountVariable> countVariables = VariableHandler.getCountVariables();

                            joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                            for(BooleanVariable booleanVariable : booleanVariables) {
                                if(!booleanVariable.getOwner().equals(player.getName())) continue;
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(booleanVariable.getValue()));
                            }

                            for(CountVariable countVariable : countVariables) {
                                if(!countVariable.getOwner().equals(player.getName())) continue;
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(countVariable.getValue()));
                            }
                        }
                    }
                }
            }

            for(Map.Entry<String, String> lookFor : replace.entrySet()) {
                joinedArgs = joinedArgs
                        .replace(lookFor.getKey(), lookFor.getValue());
            }

            System.out.println("watchdog:" + channel + " | " + event + "," + joinedArgs);
            player.sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                    byteArray(event + "," + joinedArgs));
        }
    }
}
