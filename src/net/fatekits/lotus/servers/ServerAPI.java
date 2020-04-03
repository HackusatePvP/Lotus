package net.fatekits.lotus.servers;

import net.fatekits.lotus.Lotus;
import org.bukkit.Bukkit;

public class ServerAPI {

    public static Server getServer(String name) {
        return Lotus.getPlugin().getServerManager().getServer(name);
    }

    public static int getTotalCount() {
        int count = Bukkit.getOnlinePlayers().size();
        for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("servers").getKeys(false)) {
            Server server = Lotus.getPlugin().getServerManager().getServer(s);
            count = count + server.getOnline();
        }
        return count;
    }
}
