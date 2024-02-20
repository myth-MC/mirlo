package me.u8092.mirlo.api;

import me.u8092.mirlo.api.channel.MirloChannel;
import me.u8092.mirlo.api.channel.MirloChannelManager;
import me.u8092.mirlo.api.config.MirloConfiguration;
import me.u8092.mirlo.api.event.MirloEventManager;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import me.u8092.mirlo.api.variable.MirloVariableManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Mirlo {
    @NotNull
    static Mirlo get() {
        return MirloSupplier.get();
    }

    void reload();

    void registerChannels(List<MirloChannel> channels);

    void sendMirloMessage(MirloMessage message);

    @NotNull LoggerWrapper getLogger();

    @NotNull MirloConfiguration getConfig();

    @NotNull
    default MirloChannelManager getChannelManager() {
        return MirloChannelManager.INSTANCE;
    }

    @NotNull
    default MirloVariableManager getVariableManager() {
        return MirloVariableManager.INSTANCE;
    }

    @NotNull
    default MirloEventManager getEventManager() {
        return MirloEventManager.INSTANCE;
    }
}
