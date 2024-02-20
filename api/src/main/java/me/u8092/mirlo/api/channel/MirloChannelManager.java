package me.u8092.mirlo.api.channel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.channel.impl.BasicMirloChannel;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MirloChannelManager {
    public static final MirloChannelManager INSTANCE = new MirloChannelManager();
    private static final List<MirloChannel> CHANNELS = new ArrayList<>();

    public void add(final @NotNull MirloChannel channel) {
        CHANNELS.add(channel);
    }

    public void remove(final @NotNull MirloChannel channel) {
        CHANNELS.remove(channel);
    }

    public List<MirloChannel> get() {
        return CHANNELS;
    }

    public MirloChannel get(String id) {
        for (MirloChannel channel : CHANNELS) {
            if (channel.id().equals(id)) return channel;
        }

        return null;
    }

    @ApiStatus.Internal
    public void initialize() {
        ConfigurationSection section = Mirlo.get().getConfig().getVariables().getSection();
        for (String id : section.getKeys(false)) {
            List<String> send = section.getStringList(id + ".send");
            List<String> receive = section.getStringList(id + ".receive");

            add(new BasicMirloChannel(id, send, receive));
        }
    }
}
