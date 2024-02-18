package me.u8092.watchdog.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.u8092.watchdog.Main;
import me.u8092.watchdog.variables.BooleanVariable;
import me.u8092.watchdog.variables.CountVariable;
import me.u8092.watchdog.variables.VariableHandler;
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
            Main.getInstance().getLogger().info("Event '" + event + "' called");
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
                Main.getInstance().getLogger().info("Sending '" + event + joinedArgs + "' to channel 'watchdog:" + channel + "'");
            }

            player.sendPluginMessage(Main.getInstance(), "watchdog:" + channel,
                    byteArray(event + joinedArgs));
        }
    }
}
