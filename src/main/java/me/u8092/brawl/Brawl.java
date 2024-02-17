package me.u8092.brawl;

import me.u8092.alzalibs.clans.ClansAPI;
import me.u8092.alzalibs.clans.IClansAPI;
import me.u8092.alzalibs.database.ClansDataSQL;
import me.u8092.alzalibs.database.UsersDataSQL;
import me.u8092.alzalibs.users.IUsersAPI;
import me.u8092.alzalibs.users.UsersAPI;
import me.u8092.brawl.commands.KitCommand;
import me.u8092.brawl.match.kits.ArcherKit;
import me.u8092.brawl.match.kits.BruteKit;
import me.u8092.brawl.match.kits.KnightKit;
import org.bukkit.plugin.java.JavaPlugin;

public class Brawl extends JavaPlugin {
    @Override
    public void onEnable() {
        initDatabase();
        registerKits();
        registerCommands();
    }
    @Override
    public void onDisable() {

    }

    private static void initDatabase() {
        IUsersAPI usersApi = new UsersDataSQL();
        UsersAPI.setApi(usersApi);

        IClansAPI clansApi = new ClansDataSQL();
        ClansAPI.setApi(clansApi);
    }

    private void registerKits() {
        new ArcherKit();
        new BruteKit();
        new KnightKit();
    }

    private void registerCommands() {
        this.getCommand("kit").setExecutor(new KitCommand());
    }
}