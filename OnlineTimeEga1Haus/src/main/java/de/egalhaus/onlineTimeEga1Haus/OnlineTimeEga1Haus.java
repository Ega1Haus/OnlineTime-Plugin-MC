package de.egalhaus.onlineTimeEga1Haus;

import de.egalhaus.onlineTimeEga1Haus.OnlineTime.OnlinetimeCommand;
import de.egalhaus.onlineTimeEga1Haus.OnlineTime.PlaytimeGUI;
import de.egalhaus.onlineTimeEga1Haus.OnlineTime.PlaytimeListener;
import de.egalhaus.onlineTimeEga1Haus.OnlineTime.PlaytimeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class OnlineTimeEga1Haus extends JavaPlugin {

    private PlaytimeManager playtimeManager;
    private PlaytimeGUI playtimeGUI;

    @Override
    public void onEnable() {
        getLogger().info("OnlineTimeEga1Haus aktiviert!");

        this.playtimeManager = new PlaytimeManager(this);
        this.playtimeGUI = new PlaytimeGUI(playtimeManager);
        getCommand("onlinetime").setExecutor(new OnlinetimeCommand(playtimeManager, playtimeGUI));

        Bukkit.getPluginManager().registerEvents(playtimeGUI, this);
        Bukkit.getPluginManager().registerEvents(new PlaytimeListener(playtimeManager), this);
    }

    @Override
    public void onDisable() {
        playtimeManager.savePlayerData();
        getLogger().info("OnlineTimeEga1Haus deaktiviert!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("onlinetime") && sender instanceof Player) {
            Player player = (Player) sender;
            playtimeManager.updatePlaytime(player.getUniqueId());  // Aktualisiert die Zeit sofort
            playtimeGUI.openPlaytimeMenu(player);
            return true;
        }
        return false;
    }
}