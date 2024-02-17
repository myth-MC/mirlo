package me.u8092.watchdog;

import me.u8092.alzalibs.clans.ClansAPI;
import me.u8092.alzalibs.clans.IClansAPI;
import me.u8092.alzalibs.database.ClansDataSQL;
import me.u8092.alzalibs.database.UsersDataSQL;
import me.u8092.alzalibs.users.IUsersAPI;
import me.u8092.alzalibs.users.UsersAPI;
import me.u8092.watchdog.commands.KitCommand;
import me.u8092.watchdog.options.ClansOptions;
import me.u8092.watchdog.options.PlayersOptions;
import me.u8092.watchdog.options.SessionsOptions;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    ClansOptions clansOptions = getConfig().getSerializable("", ClansOptions.class);

    @Override
    public void onLoad() {
        instance = this;

        ConfigurationSerialization.registerClass(ClansOptions.class);
        ConfigurationSerialization.registerClass(PlayersOptions.class);
        ConfigurationSerialization.registerClass(SessionsOptions.class);
    }

    @Override
    public void onEnable() {
        saveResource("config.yml", false);

        saveDefaultConfig();

        initDatabase();
        registerCommands();

        System.out.println(clansOptions.friendlyFire());
    }
    @Override
    public void onDisable() {

    }

    public static Main getInstance() { return instance; }

    private static void initDatabase() {
        IUsersAPI usersApi = new UsersDataSQL();
        UsersAPI.setApi(usersApi);

        IClansAPI clansApi = new ClansDataSQL();
        ClansAPI.setApi(clansApi);

        //ClansDataSQL.load(DB_HOST, DB_DATABASE, DB_USER, DB_PASSWORD, DB_AUTORECONNECT);
        //UsersDataSQL.load(DB_HOST, DB_DATABASE, DB_USER, DB_PASSWORD, DB_AUTORECONNECT);
    }

    private void registerCommands() {
        this.getCommand("kit").setExecutor(new KitCommand());
    }
}