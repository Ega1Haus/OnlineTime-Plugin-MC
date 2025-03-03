package de.egalhaus.onlineTimeEga1Haus.OnlineTime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlaytimeManager {
    private final JavaPlugin plugin;
    private final File playerDataFile;
    private final FileConfiguration playerDataConfig;
    private final HashMap<UUID, Long> loginTimes = new HashMap<>();

    public PlaytimeManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerDataFile = new File(plugin.getDataFolder(), "players.yml");

        if (!playerDataFile.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public void savePlayerData() {
        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlaytime(UUID uuid) {
        long savedPlaytime = playerDataConfig.getLong(uuid.toString(), 0);
        if (loginTimes.containsKey(uuid)) {
            long loginTime = loginTimes.get(uuid);
            long sessionTime = (System.currentTimeMillis() / 1000) - loginTime;
            playerDataConfig.set(uuid.toString(), savedPlaytime + sessionTime);
            loginTimes.put(uuid, System.currentTimeMillis() / 1000);  // Startzeit aktualisieren
            savePlayerData();
        }
    }

    public long getCurrentPlaytime(UUID uuid) {
        long savedPlaytime = playerDataConfig.getLong(uuid.toString(), 0);
        if (loginTimes.containsKey(uuid)) {
            long loginTime = loginTimes.get(uuid);
            long currentSessionTime = (System.currentTimeMillis() / 1000) - loginTime;
            return savedPlaytime + currentSessionTime;
        }
        return savedPlaytime;
    }

    public void playerJoined(UUID uuid) {
        loginTimes.put(uuid, System.currentTimeMillis() / 1000);
    }

    public void playerLeft(UUID uuid) {
        updatePlaytime(uuid);
        loginTimes.remove(uuid);
    }
}