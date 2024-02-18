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
                for(String variable : Objects.requireNonNull(configuration.getConfigurationSection("variables")).getKeys(false)) {
                    if(!(configuration.getString("variables." + variable + ".scope").equals("player"))) return;
                    if(configuration.getString("variables." + variable + ".type").equals("int")) {
                        for(String increaseEvent : configuration.getStringList("variables." + variable + ".increase")) {
                            if(increaseEvent.equals("PLAYER_DEATH_EVENT")) {
                                IntVariable intVariable = VariableHandler.getIntVariable(victim.getName(), variable);
                                intVariable.setValue(intVariable.getValue() + 1);
                                System.out.print(intVariable);
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

                for(String channel : Objects.requireNonNull(configuration.getConfigurationSection("channels")).getKeys(false)) {
                    for(String sendEvents : configuration.getStringList("channels." + channel + ".send")) {
                        List<String> fullEvent = List.of(sendEvents.split(","));

                        //DEBUG
                        System.out.println(fullEvent);

                        if(fullEvent.get(0).equals("PLAYER_DEATH_EVENT")) {
                            if(fullEvent.size() > 1) {
                                String joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                                //DEBUG
                                System.out.println(joinedArgs);

                                for(String variable : Objects.requireNonNull(configuration.getConfigurationSection("variables")).getKeys(false)) {
                                    if(!configuration.getString("variables." + variable + ".scope").equals("player")) return;
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
                                        byteArray("death," + killer.getName() + "," + joinedArgs));
                                return;
                            }

                            ((Player) victim).sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                                    byteArray("death," + killer.getName()));
                        }

                        if(fullEvent.get(0).equals("PLAYER_KILL_EVENT")) {
                            if(fullEvent.size() > 1) {
                                String joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');
                                for(String variable : Objects.requireNonNull(configuration.getConfigurationSection("variables")).getKeys(false)) {
                                    if(!configuration.getString("variables." + variable + ".scope").equals("player")) return;
                                    if(configuration.getString("variables." + variable + ".type").equals("int")) {
                                        int value = VariableHandler.getIntVariable(killer.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }

                                    if(configuration.getString("variables." + variable + ".type").equals("boolean")) {
                                        boolean value = VariableHandler.getBooleanVariable(killer.getName(), variable).getValue();
                                        joinedArgs = joinedArgs.replace(variable, String.valueOf(value));
                                    }
                                }

                                ((Player) killer).sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                                        byteArray("kill," + killer.getName() + "," + joinedArgs));
                                return;
                            }

                            ((Player) killer).sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                                    byteArray("kill," + killer.getName()));
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

    public void process(Entity killer, Entity victim) {
        if(killer instanceof Player && victim instanceof Player) {
            // clan DeathEvent (Player kills player)
            if(configuration.getBoolean("clans.deathEvent.players")) {
                ((Player)victim).sendPluginMessage(Main.getInstance(), "watchdog:clans", byteArray("death,player," + killer.getName()));
            }

            // clan KillEvent (Player kills player)
            if(configuration.getBoolean("clans.killEvent.players")) {
                ((Player)killer).sendPluginMessage(Main.getInstance(), "watchdog:clans", byteArray("kill,player," + victim.getName()));
            }

            // players DeathEvent (Player kills player)
            if(configuration.getBoolean("players.deathEvent.players")) {
                ((Player)victim).sendPluginMessage(Main.getInstance(), "watchdog:players", byteArray("death,player," + killer.getName()));
            }

            // players KillEvent (Player kills player)
            if(configuration.getBoolean("players.killEvent.players")) {
                ((Player)victim).sendPluginMessage(Main.getInstance(), "watchdog:players", byteArray("death,player," + killer.getName()));
            }
        }

        if(killer instanceof Player && victim instanceof Creature) {
            if(configuration.getBoolean("players.killEvent.creatures")) {
                ((Player)killer).sendPluginMessage(Main.getInstance(), "watchdog:players", byteArray("kill,player," + killer.getName()));
            }
        }

        if(killer instanceof Creature && victim instanceof Player) {
            // clan DeathEvent (Creature kills player)
            if(configuration.getBoolean("clans.deathEvent.creatures")) {
                ((Player)victim).sendPluginMessage(Main.getInstance(), "watchdog:clans", byteArray("death,player," + killer.getName()));
            }

            // player DeathEvent (Creature kills player)
            if(configuration.getBoolean("players.deathEvent.creatures")) {
                ((Player)victim).sendPluginMessage(Main.getInstance(), "watchdog:players", byteArray("death,player," + killer.getName()));
            }
        }
    }
}
