package me.u8092.mirlo.bukkit;

import lombok.Getter;
import me.u8092.mirlo.api.Mirlo;
import me.u8092.mirlo.api.logger.LoggerWrapper;
import me.u8092.mirlo.bukkit.commands.ReloadCommand;
import me.u8092.mirlo.bukkit.listeners.EntityDamageByEntityListener;
import me.u8092.mirlo.bukkit.listeners.EntityDeathListener;
import me.u8092.mirlo.bukkit.listeners.MirloMessageSentListener;
import me.u8092.mirlo.bukkit.listeners.PlayerJoinListener;
import me.u8092.mirlo.bukkit.util.DebugUtil;
import me.u8092.mirlo.common.boot.MirloBootstrap;
import me.u8092.mirlo.common.variables.BooleanVariable;
import me.u8092.mirlo.common.variables.CountVariable;
import me.u8092.mirlo.common.variables.VariableHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public final class MirloBukkit extends MirloBootstrap<MirloBukkitPlugin> {
    public static MirloBukkit INSTANCE;
    private static boolean isPaper;

    public MirloBukkit(final @NotNull MirloBukkitPlugin plugin) {
        super(plugin);
        INSTANCE = this;
    }

    private final LoggerWrapper logger = new LoggerWrapper() {
        @Override
        public void info(String message, Object... args) {
            getPlugin().getLogger().info(buildFullMessage(message, args));
        }

        @Override
        public void warn(String message, Object... args) {
            getPlugin().getLogger().warning(buildFullMessage(message, args));
        }

        @Override
        public void error(String message, Object... args) {
            getPlugin().getLogger().severe(buildFullMessage(message, args));
        }
    };

    @Override
    public void enable() {
        INSTANCE.getPlugin().saveDefaultConfig();

        try {
            Class.forName("net.kyori.adventure.Adventure");
            isPaper = true;
            DebugUtil.info("This server seems to be running Paper");
        } catch(ClassNotFoundException ignored) {

        }

        registerGlobalVariables();
        registerChannels();
        registerListeners();

        registerCommands();
    }

    public static boolean isPaper() { return isPaper; }

    private void registerChannels() {
        for(String channel : Objects.requireNonNull(INSTANCE.getPlugin().getConfig().getConfigurationSection("channels")).getKeys(false)) {
            INSTANCE.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(INSTANCE.getPlugin(), "mirlo:" + channel);

            if(INSTANCE.getPlugin().getConfig().getBoolean("debug")) DebugUtil.info("Registered plugin channel with ID 'mirlo:" + channel + "'");
        }
    }

    private void registerGlobalVariables() {
        for(String variable : Objects.requireNonNull(INSTANCE.getPlugin().getConfig().getConfigurationSection("variables")).getKeys(false)) {
            if(INSTANCE.getPlugin().getConfig().getString("variables." + variable + ".scope").equals("global")) {
                if(INSTANCE.getPlugin().getConfig().getString("variables." + variable + ".type").equals("count")) {
                    VariableHandler.addCountVariable(new CountVariable(
                            variable,
                            INSTANCE.getPlugin(). getConfig().getInt("variables." + variable + ".default"),
                            INSTANCE.getPlugin().getConfig().getStringList("variables." + variable + ".increase"),
                            INSTANCE.getPlugin().getConfig().getStringList("variables." + variable + ".decrease"),
                            INSTANCE.getPlugin().getConfig().getStringList("variables." + variable + ".reset"),
                            "global"
                    ));

                    if(INSTANCE.getPlugin().getConfig().getBoolean("debug")) DebugUtil.info("Registered new global CountVariable '" + variable + "'");
                }

                if(INSTANCE.getPlugin().getConfig().getString("variables." + variable + ".type").equals("boolean")) {
                    VariableHandler.addBooleanVariable(new BooleanVariable(
                            variable,
                            INSTANCE.getPlugin().getConfig().getBoolean("variables." + variable + ".default"),
                            INSTANCE.getPlugin().getConfig().getStringList("variables." + variable + ".true"),
                            INSTANCE.getPlugin().getConfig().getStringList("variables." + variable + ".false"),
                            INSTANCE.getPlugin().getConfig().getStringList("variables." + variable + ".switch"),
                            INSTANCE.getPlugin().getConfig().getStringList("variables." + variable + ".reset"),
                            "global"
                    ));

                    if(INSTANCE.getPlugin().getConfig().getBoolean("debug")) DebugUtil.info("Registered new global BooleanVariable '" + variable + "'");
                }
            }
        }
    }

    private void registerListeners() {
        // Bukkit listeners
        INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), INSTANCE.getPlugin());
        INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new EntityDeathListener(), INSTANCE.getPlugin());
        INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new PlayerJoinListener(), INSTANCE.getPlugin());

        // Mirlo listeners
        Mirlo.get().getEventManager().registerListener(new MirloMessageSentListener());
    }

    private void registerCommands() {
        INSTANCE.getPlugin().getCommand("mirloreload").setExecutor(new ReloadCommand());
    }
}
