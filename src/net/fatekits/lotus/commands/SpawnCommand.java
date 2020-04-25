package net.fatekits.lotus.commands;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private FileConfiguration config = Lotus.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (config.getString("spawn.x") != null) {
                Location spawn = new Location(Bukkit.getWorld(config.getString("spawn.world")), config.getDouble("spawn.x"), config.getInt("spawn.y"), config.getDouble("spawn.x"), config.getFloat("spawn.pitch"), config.getFloat("spawn.yaw"));
                player.teleport(spawn);
            } else {
                player.sendMessage(StringUtil.format("&cSpawn is not defined."));
            }
        } else {
            if (!player.hasPermission("lotus.admin")) {
                player.sendMessage(StringUtil.format(config.getString("no-permissions")));
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    Location spawn = player.getLocation();
                    config.set("spawn.world", spawn.getWorld().getName());
                    config.set("spawn.x", spawn.getX());
                    config.set("spawn.y", spawn.getBlockY());
                    config.set("spawn.z", spawn.getZ());
                    config.set("spawn.pitch", spawn.getPitch());
                    config.set("spawn.yaw", spawn.getYaw());
                    player.sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("spawn-set-location")));
                } else {
                    player.sendMessage(StringUtil.format("&c/spawn set"));
                }
            }
        }
        return false;
    }
}
