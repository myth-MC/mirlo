package me.u8092.watchdog.listeners;

import me.u8092.watchdog.Main;
import me.u8092.watchdog.variables.IntVariable;
import me.u8092.watchdog.variables.VariableHandler;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Objects;

import static me.u8092.watchdog.util.MessagerUtil.byteArray;

public class EntityDeathListener implements Listener {
    private final FileConfiguration configuration = Main.getInstance().getConfig();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity victim = event.getEntity();
        Entity killer = Objects.requireNonNull(victim.getLastDamageCause()).getEntity();

        if(victim instanceof Player) {
            if(killer instanceof Player) {
                /*
                for(String variable : Objects.requireNonNull(configuration.getConfigurationSection("variables")).getKeys(false)) {
                    if(!(configuration.getString("variables." + variable + ".scope").equals("player"))) return;
                    if(configuration.getString("variables." + variable + ".type").equals("int")) {
                        for(String increaseEvent : configuration.getStringList("variables." + variable + ".increase")) {
                            if(increaseEvent.equals("PLAYER_DEATH_EVENT")) {
                                IntVariable intVariable = VariableHandler.getIntVariable(victim.getName(), variable);
                                intVariable.setValue(intVariable.getValue() + 1);
                            }

                            if(increaseEvent.equals("PLAYER_KILL_EVENT")) {
                                IntVariable intVariable = VariableHandler.getIntVariable(killer.getName(), variable);
                                intVariable.setValue(intVariable.getValue() + 1);
                            }
                        }

                        for(String decreaseEvent : configuration.getStringList("variables." + variable + ".decrease")) {
                            if(decreaseEvent.equals("PLAYER_DEATH_EVENT")) {
                                IntVariable intVariable = VariableHandler.getIntVariable(victim.getName(), variable);
                                intVariable.setValue(intVariable.getValue() - 1);
                            }

                            if(decreaseEvent.equals("PLAYER_KILL_EVENT")) {
                                IntVariable intVariable = VariableHandler.getIntVariable(killer.getName(), variable);
                                intVariable.setValue(intVariable.getValue() - 1);
                            }
                        }

                        for(String resetEvent : configuration.getStringList("variables." + variable + ".reset")) {
                            if(resetEvent.equals("PLAYER_DEATH_EVENT")) {
                                IntVariable intVariable = VariableHandler.getIntVariable(victim.getName(), variable);
                                intVariable.setValue(intVariable.getDefaultValue());
                            }

                            if(resetEvent.equals("PLAYER_KILL_EVENT")) {
                                IntVariable intVariable = VariableHandler.getIntVariable(killer.getName(), variable);
                                intVariable.setValue(intVariable.getDefaultValue());
                            }
                        }
                    }
                }
                   */
                for(String channel : configuration.getConfigurationSection("channels").getKeys(false)) {
                    for(String sendEvents : configuration.getStringList("channels." + channel + ".send")) {
                        List<String> fullEvent = List.of(sendEvents.split(","));
                        System.out.println(fullEvent);

                        if(fullEvent.get(0).equals("PLAYER_DEATH_EVENT")) {
                            if(fullEvent.size() > 1) {
                                String joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                                for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
                                    if(!(configuration.getString("variables." + variable + ".scope").equals("player"))) return;
                                    if(configuration.getString("variables." + variable + ".type").equals("int")) {
                                        int value = VariableHandler.getIntVariable(victim.getName(), variable).getValue();
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
                            System.out.println(fullEvent.size());
                            if(fullEvent.size() > 1) {
                                String joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                                System.out.println("1_" + joinedArgs);
                                System.out.println(configuration.getConfigurationSection("variables").getKeys(false));
                                System.out.println(Objects.requireNonNull(configuration.getConfigurationSection("variables")).getKeys(false));

                                if(configuration.getString("variables." + fullEvent.get(1) + ".type").equals("int")) {
                                    int value = VariableHandler.getIntVariable(killer.getName(), fullEvent.get(1)).getValue();
                                    joinedArgs = joinedArgs.replace(fullEvent.get(1), String.valueOf(value));
                                }

                                if(configuration.getString("variables." + fullEvent.get(1) + ".type").equals("boolean")) {
                                    boolean value = VariableHandler.getBooleanVariable(killer.getName(), fullEvent.get(1)).getValue();
                                    joinedArgs = joinedArgs.replace(fullEvent.get(1), String.valueOf(value));
                                }

                                /*
                                for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
                                    System.out.println(variable);
                                    if(!(configuration.getString("variables." + variable + ".scope").equals("player"))) return;

                                    if(configuration.getString("variables." + variable + ".type").equals("int")) {
                                        int value = VariableHandler.getIntVariable(killer.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }

                                    if(configuration.getString("variables." + variable + ".type").equals("boolean")) {
                                        boolean value = VariableHandler.getBooleanVariable(killer.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }
                                }

                                 */

                                System.out.println("2 " + joinedArgs);

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
