package net.fatekits.lotus.servers;

import net.fatekits.lotus.Lotus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerTask extends BukkitRunnable {
    private int left = 0;

    @Override
    public void run() {
        ++left;
        if (left == 60) {
            for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("servers").getKeys(false)) {
                FileConfiguration config = Lotus.getPlugin().getConfig();
                String path = "servers." + s;
                Server server = Lotus.getPlugin().getServerManager().getServer(s);
                Lotus.getPlugin().getServerManager().getOnlineCount(config.getString(path + ".ip"), config.getInt(path + ".port"), server);
                left = 0;
            }
        }
    }
}
