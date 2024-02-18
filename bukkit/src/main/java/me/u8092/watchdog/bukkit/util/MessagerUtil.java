package me.u8092.watchdog.bukkit.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.u8092.watchdog.bukkit.Main;
import me.u8092.watchdog.commons.variables.BooleanVariable;
import me.u8092.watchdog.commons.variables.CountVariable;
import me.u8092.watchdog.commons.variables.VariableHandler;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessagerUtil {
    private static final FileConfiguration configuration = Main.getInstance().getConfig();
    private static final boolean DEBUG = configuration.getBoolean("debug");

    public static byte[] byteArray(List<String> bytes) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for(String b : bytes) {
            out.writeUTF(b);
        }

        return out.toByteArray();
    }

    public static byte[] byteArray(String bytesSeparatedByComma) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        List<String> bytes = List.of(bytesSeparatedByComma.split(","));

        for(String b : bytes) {
            out.writeUTF(b);
        }

        return out.toByteArray();
    }

    public static void send(Player player, String event, Map<String, String> replace) {
        List<String> channelsToSendMessage = new ArrayList<>();
        String joinedArgs = "";

        if(DEBUG) {
            DebugUtil.info("Event '" + event + "' called");
        }

        for(String channel : configuration.getConfigurationSection("channels").getKeys(false)) {
            for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                List<String> fullEvent = List.of(unformattedEvent.split(","));
                if(fullEvent.contains(event)) channelsToSendMessage.add(channel);
            }
        }

        // Replace variables
        for(String channel : channelsToSendMessage) {
            for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
                // Global variables
                if(configuration.getString("variables." + variable + ".scope").equals("global")) {
                    for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                        List<String> fullEvent = List.of(unformattedEvent.split(","));

                        if(fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                            List<BooleanVariable> booleanVariables = VariableHandler.getBooleanVariables("global");
                            List<CountVariable> countVariables = VariableHandler.getCountVariables("global");

                            joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                            for(BooleanVariable booleanVariable : booleanVariables) {
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(booleanVariable.getValue()));
                            }

                            for(CountVariable countVariable : countVariables) {
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(countVariable.getValue()));
                            }
                        }
                    }
                }

                // Player variables
                if(configuration.getString("variables." + variable + ".scope").equals("player")) {
                    for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                        List<String> fullEvent = List.of(unformattedEvent.split(","));

                        if(fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                            List<BooleanVariable> booleanVariables = VariableHandler.getBooleanVariables(player.getName());
                            List<CountVariable> countVariables = VariableHandler.getCountVariables(player.getName());

                            joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                            for(BooleanVariable booleanVariable : booleanVariables) {
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(booleanVariable.getValue()));
                            }

                            for(CountVariable countVariable : countVariables) {
                                joinedArgs = joinedArgs
                                        .replace(variable, String.valueOf(countVariable.getValue()));
                            }
                        }
                    }
                }
            }

            // Replace lookFor
            for(Map.Entry<String, String> lookFor : replace.entrySet()) {
                joinedArgs = joinedArgs
                        .replace(lookFor.getKey(), lookFor.getValue());
            }

            if(!joinedArgs.isEmpty() && !joinedArgs.startsWith(",")) {
                joinedArgs = "," + joinedArgs;
            }

            if(DEBUG) {
                DebugUtil.info("Sending '" + event + joinedArgs + "' to channel 'watchdog:" + channel + "'");
            }

            player.sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                    byteArray(event + joinedArgs));
        }
    }

    public static void updateVariables(String event, String varOwner, boolean globalToo) {
        updateVariables(event, varOwner);
        if(globalToo) {
            updateVariables(event, "global");
        }
    }

    public static void updateVariables(String event, String varOwner) {
        for(String variable : configuration.getConfigurationSection("variables").getKeys(false)) {
            if(configuration.getString("variables." + variable + ".type").equals("count")) {
                CountVariable countVariable = VariableHandler.getCountVariable(varOwner, configuration.getString("variables." + variable + ".name"));
                if(countVariable == null) continue;

                for(String increaseEvent : countVariable.getIncreaseEvents()) {
                    if(increaseEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(Main.getInstance().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }

                for(String decreaseEvent : countVariable.getDecreaseEvents()) {
                    if(decreaseEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(Main.getInstance().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }

                for(String resetEvent : countVariable.getResetEvents()) {
                    if(resetEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(Main.getInstance().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }
            }

            if(configuration.getString("variables." + variable + ".type").equals("boolean")) {
                BooleanVariable booleanVariable = VariableHandler.getBooleanVariable(varOwner, configuration.getString("variables." + variable + ".name"));
                if(booleanVariable == null) continue;

                for(String trueEvent : booleanVariable.getTrueEvents()) {
                    if(trueEvent.equals(event)) {
                        booleanVariable.setValue(true);
                        if(Main.getInstance().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String falseEvent : booleanVariable.getFalseEvents()) {
                    if(falseEvent.equals(event)) {
                        booleanVariable.setValue(false);
                        if(Main.getInstance().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String switchEvent : booleanVariable.getSwitchEvents()) {
                    if(switchEvent.equals(event)) {
                        booleanVariable.setValue(!booleanVariable.getValue());
                        if(Main.getInstance().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String resetEvent : booleanVariable.getResetEvents()) {
                    if(resetEvent.equals(event)) {
                        booleanVariable.setValue(booleanVariable.getDefaultValue());
                        if(Main.getInstance().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }
            }
        }
    }
}
