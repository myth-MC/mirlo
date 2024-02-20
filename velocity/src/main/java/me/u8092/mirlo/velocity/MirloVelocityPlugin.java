package me.u8092.mirlo.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;

import java.nio.file.Path;

@Getter
public class MirloVelocityPlugin {
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private MirloVelocity bootstrap;

    @Inject
    public MirloVelocityPlugin(final ProxyServer server,
                               final Logger logger,
                               final @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void handle(final ProxyInitializeEvent event) {
        bootstrap = new MirloVelocity(this);
        bootstrap.initialize();
    }

    @Subscribe
    public void handle(final ProxyShutdownEvent event) {
        bootstrap.shutdown();
    }
}
