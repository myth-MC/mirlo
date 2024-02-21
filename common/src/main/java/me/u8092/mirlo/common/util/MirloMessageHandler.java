package me.u8092.mirlo.common.util;

import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.message.MirloMessage;
import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.channel.impl.BasicMirloChannel;
import me.u8092.mirlo.api.variable.MirloVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MirloMessageHandler {
    public static List<MirloMessage> formatEvent(String owner, String event, Map<String, String> replace) {
        List<MirloMessage> toSend = new ArrayList<>();
        String joinedArgs = "";

        for (MirloChannel channel : Mirlo.get().getChannelManager().get()) {
            if(channel instanceof BasicMirloChannel basicMirloChannel) {
                for (String unformattedEvent : basicMirloChannel.send()) {
                    List<String> fullEvent = new ArrayList<>(List.of(unformattedEvent.split(",")));
                    if (fullEvent.contains(event)) {
                        if (fullEvent.get(0).equals(event) && fullEvent.size() > 1) {
                            joinedArgs = String.join(",", fullEvent.subList(1, fullEvent.size()));

                            for (MirloVariable<?> variable : Mirlo.get().getVariableManager().get()) {
                                if (!variable.owner().equals(owner) && !variable.owner().equals("global")) continue;

                                joinedArgs = joinedArgs
                                        .replace(variable.name(), String.valueOf(variable.value()));
                            }
                        }
                    }
                }
            }

            // Replace lookFor
            for (Map.Entry<String, String> lookFor : replace.entrySet()) {
                joinedArgs = joinedArgs
                        .replace(lookFor.getKey(), lookFor.getValue());
            }

            // Put , at start
            if (!joinedArgs.isEmpty() && !joinedArgs.startsWith(",")) {
                joinedArgs = "," + joinedArgs;
            }

            toSend.add(new MirloMessage(channel.id(), event + joinedArgs));
        }

        return toSend;
    }
}
