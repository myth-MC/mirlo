package me.u8092.mirlo.common.util;

import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.MirloMessage;
import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.variable.MirloVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MirloMessageHandler {
    public static List<MirloMessage> formatEvent(String owner, String event, Map<String, String> replace) {
        List<MirloMessage> toSend = new ArrayList<>();
        StringBuilder joinedArgs = new StringBuilder();

        for (MirloChannel channel : Mirlo.get().getChannelManager().get()) {
            for (String unformattedEvent : channel.send()) {
                List<String> fullEvent = new ArrayList<>(List.of(unformattedEvent.split(",")));
                if (fullEvent.contains(event)) {
                    if (fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                        joinedArgs = new StringBuilder(String.join(",", fullEvent.remove(0)));

                        for (MirloVariable<?> variable : Mirlo.get().getVariableManager().get()) {
                            if (!variable.owner().equals(owner) && !variable.owner().equals("global")) continue;

                            joinedArgs = new StringBuilder(joinedArgs.toString().replace(variable.name(), String.valueOf(variable.value())));
                        }
                    }
                }
            }

            // Replace lookFor
            for (Map.Entry<String, String> lookFor : replace.entrySet()) {
                joinedArgs = new StringBuilder(joinedArgs.toString()
                        .replace(lookFor.getKey(), lookFor.getValue()));
            }

            // Put , at start
            if ((!joinedArgs.isEmpty()) && !joinedArgs.toString().startsWith(",")) {
                joinedArgs.insert(0, ",");
            }

            toSend.add(new MirloMessage(channel.id(), "not-available-yet", event + joinedArgs));
        }

        return toSend;
    }
}
