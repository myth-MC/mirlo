package me.u8092.brawl.match;

import me.u8092.alzalibs.users.UsersAPI;

public class BrawlPlayer {
    private String playerName;
    private int streak;

    public BrawlPlayer(String playerName) {
        this.playerName = playerName;
        this.streak = 0;
    }

    public String getPlayerName() { return playerName; }

    public int getStreak() { return streak; }

    public void resetStreak() { streak = 0; }

    public void addKill() {
        streak = streak + 1;
        UsersAPI.getApi().addKill(playerName);
    }

    public void addDeath() {
        UsersAPI.getApi().addDeath(playerName);
        // actionbar con mensaje (tienes x muertes)
    }

    public void addMytharites(int amount) {
        UsersAPI.getApi().addMytharites(playerName, amount);
        // actionbar con mensaje (has ganado x mitaritas)
    }

    public void removeMytharites(int amount) {
        UsersAPI.getApi().removeMytharites(playerName, amount);
        // actionbar con mensaje (has perdido x mitaritas)
    }

    public int getKills() {
        return UsersAPI.getApi().getKills(playerName);
    }
}
