package me.u8092.mirlo.minestom;

import net.minestom.server.extensions.Extension;

public class MirloMinestomExtension extends Extension {
    private MirloMinestom bootstrap;

    @Override
    public void initialize() {
        bootstrap = new MirloMinestom(this);
        bootstrap.initialize();
    }

    @Override
    public void terminate() {
        bootstrap.shutdown();
    }
}
