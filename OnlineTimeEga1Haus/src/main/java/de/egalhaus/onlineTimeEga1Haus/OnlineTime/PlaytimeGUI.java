package de.egalhaus.onlineTimeEga1Haus.OnlineTime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class PlaytimeGUI implements Listener {
    private final PlaytimeManager playtimeManager;

    public PlaytimeGUI(PlaytimeManager playtimeManager) {
        this.playtimeManager = playtimeManager;
    }

    public void openPlaytimeMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Spielzeit Menü");

        // Spielzeit-Anzeige
        ItemStack playtimeItem = new ItemStack(Material.CLOCK);
        ItemMeta playtimeMeta = playtimeItem.getItemMeta();
        if (playtimeMeta != null) {
            playtimeMeta.setDisplayName(ChatColor.GOLD + "Deine Spielzeit");

            long playtime = playtimeManager.getCurrentPlaytime(player.getUniqueId());
            long days = playtime / 86400;
            long hours = (playtime % 86400) / 3600;
            long minutes = (playtime % 3600) / 60;
            long seconds = playtime % 60;

            playtimeMeta.setLore(Arrays.asList(
                    ChatColor.YELLOW + "Gesamtspielzeit:",
                    ChatColor.WHITE + "" + days + " Tage, " + hours + " Std, " + minutes + " Min, " + seconds + " Sek"
            ));
            playtimeItem.setItemMeta(playtimeMeta);
        }

        // Belohnungs-Button
        ItemStack rewardItem = new ItemStack(Material.CHEST);
        ItemMeta rewardMeta = rewardItem.getItemMeta();
        if (rewardMeta != null) {
            rewardMeta.setDisplayName(ChatColor.GREEN + "Belohnung abholen");
            rewardMeta.setLore(Arrays.asList(
                    ChatColor.YELLOW + "Klicke hier, um eine Belohnung zu erhalten."
            ));
            rewardItem.setItemMeta(rewardMeta);
        }

        // Items setzen
        menu.setItem(4, playtimeItem); // Die Uhr ist jetzt mittig (Slot 4)
        menu.setItem(6, rewardItem);

        player.openInventory(menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.BLUE + "Spielzeit Menü")) {
            event.setCancelled(true); // Verhindert, dass Spieler Items entnehmen
        }
    }
}