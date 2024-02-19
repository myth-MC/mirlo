package me.u8092.mirlo.bukkit;

import me.u8092.mirlo.api.Mirlo;
import org.bukkit.plugin.java.JavaPlugin;

public class MirloBukkitPlugin extends JavaPlugin {
    private MirloBukkit bootstrap;

    @Override
    public void onEnable() {
        bootstrap = new MirloBukkit(this);
        bootstrap.initialize();
    }

    @Override
    public void onDisable() {
        bootstrap.shutdown();
    }
}
