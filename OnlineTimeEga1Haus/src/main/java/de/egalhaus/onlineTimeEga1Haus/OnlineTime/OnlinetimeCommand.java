package de.egalhaus.onlineTimeEga1Haus.OnlineTime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnlinetimeCommand implements CommandExecutor {

    private final PlaytimeManager playtimeManager;
    private final PlaytimeGUI playtimeGUI;

    public OnlinetimeCommand(PlaytimeManager playtimeManager, PlaytimeGUI playtimeGUI) {
        this.playtimeManager = playtimeManager;
        this.playtimeGUI = playtimeGUI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;
        playtimeManager.updatePlaytime(player.getUniqueId()); // Zeit sofort aktualisieren
        playtimeGUI.openPlaytimeMenu(player);

        return true;
    }
}