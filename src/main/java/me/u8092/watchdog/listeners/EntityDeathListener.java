package me.u8092.watchdog.listeners;

import me.u8092.watchdog.Main;
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

import java.util.List;

import static me.u8092.watchdog.util.MessagerUtil.byteArray;

public class EntityDeathListener implements Listener {
    private final FileConfiguration configuration = Main.getInstance().getConfig();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();
        if(!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent entityDamageByEntityEvent)) return;

        Entity killer = entityDamageByEntityEvent.getDamager();

        if(victim instanceof Player) {
            if(killer instanceof Player) {
                for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
                    if(!(configuration.getString("variables." + variable + ".scope").equals("player"))) continue;
                    if(configuration.getString("variables." + variable + ".type").equals("count")) {
                        for(String increaseEvent : configuration.getStringList("variables." + variable + ".increase")) {
                            if(increaseEvent.equals("PLAYER_DEATH_EVENT")) {
                                CountVariable countVariable = VariableHandler.getCountVariable(victim.getName(), variable);
                                countVariable.setValue(countVariable.getValue() + 1);
                            }

                            if(increaseEvent.equals("PLAYER_KILL_EVENT")) {
                                CountVariable countVariable = VariableHandler.getCountVariable(killer.getName(), variable);
                                countVariable.setValue(countVariable.getValue() + 1);
                            }
                        }

                        for(String decreaseEvent : configuration.getStringList("variables." + variable + ".decrease")) {
                            if(decreaseEvent.equals("PLAYER_DEATH_EVENT")) {
                                CountVariable countVariable = VariableHandler.getCountVariable(victim.getName(), variable);
                                countVariable.setValue(countVariable.getValue() - 1);
                            }

                            if(decreaseEvent.equals("PLAYER_KILL_EVENT")) {
                                CountVariable countVariable = VariableHandler.getCountVariable(killer.getName(), variable);
                                countVariable.setValue(countVariable.getValue() - 1);
                            }
                        }

                        for(String resetEvent : configuration.getStringList("variables." + variable + ".reset")) {
                            if(resetEvent.equals("PLAYER_DEATH_EVENT")) {
                                CountVariable countVariable = VariableHandler.getCountVariable(victim.getName(), variable);
                                countVariable.setValue(countVariable.getDefaultValue());
                            }

                            if(resetEvent.equals("PLAYER_KILL_EVENT")) {
                                CountVariable countVariable = VariableHandler.getCountVariable(killer.getName(), variable);
                                countVariable.setValue(countVariable.getDefaultValue());
                            }
                        }
                    }
                }

                for(String channel : configuration.getConfigurationSection("channels").getKeys(false)) {
                    for(String sendEvents : configuration.getStringList("channels." + channel + ".send")) {
                        List<String> fullEvent = List.of(sendEvents.split(","));

                        if(fullEvent.get(0).equals("PLAYER_DEATH_EVENT")) {
                            if(fullEvent.size() > 1) {
                                String joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                                for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
                                    if(variable == null) continue;

                                    if(!(configuration.getString("variables." + variable + ".scope").equals("player"))) continue;

                                    if(configuration.getString("variables." + variable + ".type").equals("int")) {
                                        int value = VariableHandler.getCountVariable(victim.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }

                                    if(configuration.getString("variables." + variable + ".type").equals("boolean")) {
                                        boolean value = VariableHandler.getBooleanVariable(victim.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }
                                }

                                ((Player) victim).sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                                        byteArray("death," + victim.getName() + "," + joinedArgs));
                            } else {
                                ((Player) victim).sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                                        byteArray("death," + victim.getName()));
                            }
                        }

                        if(fullEvent.get(0).equals("PLAYER_KILL_EVENT")) {
                            if(fullEvent.size() > 1) {
                                String joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                                for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
                                    if(variable == null) continue;

                                    if(!(configuration.getString("variables." + variable + ".scope").equals("player"))) continue;

                                    if(configuration.getString("variables." + variable + ".type").equals("int")) {
                                        int value = VariableHandler.getCountVariable(killer.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }

                                    if(configuration.getString("variables." + variable + ".type").equals("boolean")) {
                                        boolean value = VariableHandler.getBooleanVariable(killer.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }
                                }

                                ((Player) killer).sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                                        byteArray("kill," + killer.getName() + "," + joinedArgs));
                            } else {
                                ((Player) killer).sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                                        byteArray("kill," + killer.getName()));
                            }
                        }
                    }
                }
            }

            if(killer instanceof Creature) {

            }
        }

        if(killer instanceof Player) {
            if(victim instanceof Player) {

            }

            if(victim instanceof Creature) {

            }
        }
    }
}
