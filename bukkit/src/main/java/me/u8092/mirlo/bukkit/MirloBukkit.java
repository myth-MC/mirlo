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
import org.simpleyaml.configuration.ConfigurationSection;

@Getter
public final class MirloBukkit extends MirloBootstrap<MirloBukkitPlugin> {
    public static MirloBukkit INSTANCE;
    private static boolean isPaper;

    public MirloBukkit(final @NotNull MirloBukkitPlugin plugin) {
        super(plugin, plugin.getDataFolder());
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
        for(String channel : Mirlo.get().getConfig().getChannels().getSection().getKeys(false)) {
            INSTANCE.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(INSTANCE.getPlugin(), "mirlo:" + channel);

            if(Mirlo.get().getConfig().getSettings().isDebug()) DebugUtil.info("Registered plugin channel with ID 'mirlo:" + channel + "'");
        }
    }

    private void registerGlobalVariables() {
        ConfigurationSection section = Mirlo.get().getConfig().getVariables().getSection();
        for(String variable : section.getKeys(false)) {
            if(section.getString(variable + ".scope").equals("global")) {
                if(section.getString(variable + ".type").equals("count")) {
                    VariableHandler.addCountVariable(new CountVariable(
                            variable,
                            section.getInt(variable + ".default"),
                            section.getStringList(variable + ".increase"),
                            section.getStringList(variable + ".decrease"),
                            section.getStringList(variable + ".reset"),
                            "global"
                    ));

                    if(Mirlo.get().getConfig().getSettings().isDebug()) DebugUtil.info("Registered new global CountVariable '" + variable + "'");
                }

                if(section.getString(variable + ".type").equals("boolean")) {
                    VariableHandler.addBooleanVariable(new BooleanVariable(
                            variable,
                            section.getBoolean(variable + ".default"),
                            section.getStringList(variable + ".true"),
                            section.getStringList(variable + ".false"),
                            section.getStringList(variable + ".switch"),
                            section.getStringList(variable + ".reset"),
                            "global"
                    ));

                    if(Mirlo.get().getConfig().getSettings().isDebug()) DebugUtil.info("Registered new global BooleanVariable '" + variable + "'");
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
