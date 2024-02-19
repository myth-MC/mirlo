package me.u8092.mirlo.bukkit.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.event.impl.MirloMessageSentEvent;
import me.u8092.mirlo.bukkit.MirloBukkit;
import me.u8092.mirlo.common.variables.BooleanVariable;
import me.u8092.mirlo.common.variables.CountVariable;
import me.u8092.mirlo.common.variables.VariableHandler;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Todo: move to commons
public class MessagerUtil {
    private static final FileConfiguration configuration = MirloBukkit.INSTANCE.getPlugin().getConfig();
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
            for(BooleanVariable booleanVariable : VariableHandler.getBooleanVariables()) {
                if(!booleanVariable.getOwner().equals(player.getName()) && !booleanVariable.getOwner().equals("global")) continue;

                for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                    List<String> fullEvent = List.of(unformattedEvent.split(","));

                    if(fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                        joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                        joinedArgs = joinedArgs.replace(booleanVariable.getName(), String.valueOf(booleanVariable.getValue()));
                    }
                }
            }

            for(CountVariable countVariable : VariableHandler.getCountVariables()) {
                if(!countVariable.getOwner().equals(player.getName()) && !countVariable.getOwner().equals("global")) continue;

                for(String unformattedEvent : configuration.getStringList("channels." + channel + ".send")) {
                    List<String> fullEvent = List.of(unformattedEvent.split(","));

                    if(fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                        joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                        joinedArgs = joinedArgs.replace(countVariable.getName(), String.valueOf(countVariable.getValue()));
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
                DebugUtil.info("Sending '" + event + joinedArgs + "' to channel 'mirlo:" + channel + "'");
            }

            Mirlo.get().getEventManager().publish(new MirloMessageSentEvent(channel, "test", event + joinedArgs));
            player.sendPluginMessage(MirloBukkit.INSTANCE.getPlugin(), "mirlo:" + channel,
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
                        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }

                for(String decreaseEvent : countVariable.getDecreaseEvents()) {
                    if(decreaseEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }

                for(String resetEvent : countVariable.getResetEvents()) {
                    if(resetEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug"))
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
                        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String falseEvent : booleanVariable.getFalseEvents()) {
                    if(falseEvent.equals(event)) {
                        booleanVariable.setValue(false);
                        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String switchEvent : booleanVariable.getSwitchEvents()) {
                    if(switchEvent.equals(event)) {
                        booleanVariable.setValue(!booleanVariable.getValue());
                        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String resetEvent : booleanVariable.getResetEvents()) {
                    if(resetEvent.equals(event)) {
                        booleanVariable.setValue(booleanVariable.getDefaultValue());
                        if(MirloBukkit.INSTANCE.getPlugin().getConfig().getBoolean("debug"))
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }
            }
        }
    }
}
