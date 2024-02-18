package me.u8092.mirlo.bukkit;

import me.u8092.mirlo.bukkit.commands.ReloadCommand;
import me.u8092.mirlo.bukkit.listeners.EntityDamageByEntityListener;
import me.u8092.mirlo.bukkit.listeners.EntityDeathListener;
import me.u8092.mirlo.bukkit.listeners.PlayerJoinListener;
import me.u8092.mirlo.bukkit.util.DebugUtil;
import me.u8092.mirlo.commons.variables.BooleanVariable;
import me.u8092.mirlo.commons.variables.CountVariable;
import me.u8092.mirlo.commons.variables.VariableHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    private static Main instance;
    private static boolean isPaper;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

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
    @Override
    public void onDisable() {

    }

    public static Main getInstance() { return instance; }

    public static boolean isPaper() { return isPaper; }

    private void registerChannels() {
        for(String channel : Objects.requireNonNull(getConfig().getConfigurationSection("channels")).getKeys(false)) {
            getServer().getMessenger().registerOutgoingPluginChannel(this, "mirlo:" + channel);

            if(getConfig().getBoolean("debug")) DebugUtil.info("Registered plugin channel with ID 'mirlo:" + channel + "'");
        }
    }

    private void registerGlobalVariables() {
        for(String variable : Objects.requireNonNull(getConfig().getConfigurationSection("variables")).getKeys(false)) {
            if(getConfig().getString("variables." + variable + ".scope").equals("global")) {
                if(getConfig().getString("variables." + variable + ".type").equals("count")) {
                    VariableHandler.addCountVariable(new CountVariable(
                            variable,
                            getConfig().getInt("variables." + variable + ".default"),
                            getConfig().getStringList("variables." + variable + ".increase"),
                            getConfig().getStringList("variables." + variable + ".decrease"),
                            getConfig().getStringList("variables." + variable + ".reset"),
                            "global"
                    ));

                    if(getConfig().getBoolean("debug")) DebugUtil.info("Registered new global CountVariable '" + variable + "'");
                }

                if(getConfig().getString("variables." + variable + ".type").equals("boolean")) {
                    VariableHandler.addBooleanVariable(new BooleanVariable(
                            variable,
                            getConfig().getBoolean("variables." + variable + ".default"),
                            getConfig().getStringList("variables." + variable + ".true"),
                            getConfig().getStringList("variables." + variable + ".false"),
                            getConfig().getStringList("variables." + variable + ".switch"),
                            getConfig().getStringList("variables." + variable + ".reset"),
                            "global"
                    ));

                    if(getConfig().getBoolean("debug")) DebugUtil.info("Registered new global BooleanVariable '" + variable + "'");
                }
            }
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    private void registerCommands() {
        this.getCommand("mirloreload").setExecutor(new ReloadCommand());
    }
}