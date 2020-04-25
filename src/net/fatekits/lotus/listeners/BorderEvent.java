package net.fatekits.lotus.listeners;

import net.fatekits.lotus.Lotus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveBlockEvent;

public class BorderEvent implements Listener {
    private FileConfiguration config = Lotus.getPlugin().getConfig();

    @EventHandler
    public void onMove(PlayerMoveBlockEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        if (to.getBlockX() > Lotus.getPlugin().getConfig().getInt("border.x") || to.getBlockZ() > Lotus.getPlugin().getConfig().getInt("border.z")) {
            Location spawn = new Location(Bukkit.getWorld(config.getString("spawn.world")), config.getDouble("spawn.x"), config.getInt("spawn.y"), config.getDouble("spawn.x"), config.getFloat("spawn.pitch"), config.getFloat("spawn.yaw"));
            player.teleport(spawn);
        }
    }
}
