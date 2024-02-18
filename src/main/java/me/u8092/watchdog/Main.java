package me.u8092.watchdog;

import me.u8092.watchdog.commands.ReloadCommand;
import me.u8092.watchdog.listeners.EntityDamageByEntityListener;
import me.u8092.watchdog.listeners.EntityDeathListener;
import me.u8092.watchdog.listeners.PlayerJoinListener;
import me.u8092.watchdog.variables.BooleanVariable;
import me.u8092.watchdog.variables.CountVariable;
import me.u8092.watchdog.variables.VariableHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        //saveResource("config.yml", false);

        registerGlobalVariables();
        registerChannels();
        registerListeners();

        registerCommands();
    }
    @Override
    public void onDisable() {

    }

    public static Main getInstance() { return instance; }

    private void registerChannels() {
        for(String channel : Objects.requireNonNull(getConfig().getConfigurationSection("channels")).getKeys(false)) {
            getServer().getMessenger().registerOutgoingPluginChannel(this, "watchdog:" + channel);

            if(getConfig().getBoolean("debug")) getLogger().info("Registered plugin channel with ID 'watchdog:" + channel + "'");
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

                    if(getConfig().getBoolean("debug")) getLogger().info("Registered new CountVariable '" + variable + "'");
                }

                if(getConfig().getString("variables." + variable + ".type").equals("boolean")) {
                    VariableHandler.addBooleanVariable(new BooleanVariable(
                            variable,
                            getConfig().getBoolean("variables." + variable + ".default"),
                            "global"
                    ));

                    if(getConfig().getBoolean("debug")) getLogger().info("Registered new BooleanVariable '" + variable + "'");
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
        this.getCommand("watchdogreload").setExecutor(new ReloadCommand());
    }
}