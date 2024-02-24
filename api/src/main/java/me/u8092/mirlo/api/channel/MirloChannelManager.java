package me.u8092.mirlo.api.channel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

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
}
