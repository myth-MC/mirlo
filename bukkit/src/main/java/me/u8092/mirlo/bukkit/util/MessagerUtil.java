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
import org.bukkit.entity.Player;
import org.simpleyaml.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Todo: move to api
public class MessagerUtil {
    private static final boolean DEBUG = Mirlo.get().getConfig().getSettings().isDebug();
    
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

        ConfigurationSection section = Mirlo.get().getConfig().getChannels().getSection();
        for(String channel : section.getKeys(false)) {
            for(String unformattedEvent : section.getStringList(channel + ".send")) {
                List<String> fullEvent = List.of(unformattedEvent.split(","));
                if(fullEvent.contains(event)) channelsToSendMessage.add(channel);
            }
        }
        
        for(String channel : channelsToSendMessage) {
            for(String unformattedEvent : section.getStringList(channel + ".send")) {
                List<String> fullEvent = List.of(unformattedEvent.split(","));

                if(fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                    joinedArgs = Strings.join(fullEvent.subList(1, fullEvent.size()), ',');

                    // if variable instanceof BooleanVariable
                    for(BooleanVariable booleanVariable : VariableHandler.getBooleanVariables()) {
                        if(!booleanVariable.getOwner().equals(player.getName()) && !booleanVariable.getOwner().equals("global")) continue;

                        joinedArgs = joinedArgs.replace(booleanVariable.getName(), String.valueOf(booleanVariable.getValue()));
                    }

                    for(CountVariable countVariable : VariableHandler.getCountVariables()) {
                        if(!countVariable.getOwner().equals(player.getName()) && !countVariable.getOwner().equals("global")) continue;

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
        ConfigurationSection section = Mirlo.get().getConfig().getVariables().getSection();

        for(String variable : section.getKeys(false)) {
            if(section.getString(variable + ".type").equals("count")) {
                CountVariable countVariable = VariableHandler.getCountVariable(varOwner, section.getString(variable + ".name"));
                if(countVariable == null) continue;

                for(String increaseEvent : countVariable.getIncreaseEvents()) {
                    if(increaseEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(DEBUG)
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }

                for(String decreaseEvent : countVariable.getDecreaseEvents()) {
                    if(decreaseEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(DEBUG)
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }

                for(String resetEvent : countVariable.getResetEvents()) {
                    if(resetEvent.equals(event)) {
                        countVariable.setValue(countVariable.getValue() + 1);
                        if(DEBUG)
                            DebugUtil.info("Set CountVariable " + countVariable.getName() + " to " + countVariable.getValue() + " (" + countVariable.getOwner() + ")");
                    }
                }
            }

            if(section.getString(variable + ".type").equals("boolean")) {
                BooleanVariable booleanVariable = VariableHandler.getBooleanVariable(varOwner, section.getString(variable + ".name"));
                if(booleanVariable == null) continue;

                for(String trueEvent : booleanVariable.getTrueEvents()) {
                    if(trueEvent.equals(event)) {
                        booleanVariable.setValue(true);
                        if(DEBUG)
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String falseEvent : booleanVariable.getFalseEvents()) {
                    if(falseEvent.equals(event)) {
                        booleanVariable.setValue(false);
                        if(DEBUG)
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String switchEvent : booleanVariable.getSwitchEvents()) {
                    if(switchEvent.equals(event)) {
                        booleanVariable.setValue(!booleanVariable.getValue());
                        if(DEBUG)
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }

                for(String resetEvent : booleanVariable.getResetEvents()) {
                    if(resetEvent.equals(event)) {
                        booleanVariable.setValue(booleanVariable.getDefaultValue());
                        if(DEBUG)
                            DebugUtil.info("Set BooleanVariable " + booleanVariable.getName() + " to " + booleanVariable.getValue() + " (" + booleanVariable.getOwner() + ")");
                    }
                }
            }
        }
    }
}
