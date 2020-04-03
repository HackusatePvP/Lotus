package net.fatekits.lotus.commands;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ConsoleCommand implements CommandExecutor {
    private FileConfiguration config = Lotus.getPlugin().getLangConfig().getConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission("lotus.admin")) {
            if (args.length == 0) {

            } else {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("staff")) {
                        player.sendMessage(StringUtil.format("&c/console staff <player>"));
                    }
                }

                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("staff")) {
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null) {
                            Profile tar = Lotus.getPlugin().getProfileManager().getProfile(target.getUniqueId());
                            tar.setStaff(true);
                        } else {
                            player.sendMessage(StringUtil.format(config.getString("target-not-found")));
                        }
                    }
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("rank")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            String rank = args[2];
                            Profile tar = Lotus.getPlugin().getProfileManager().getProfile(target.getUniqueId());
                            tar.setRank(rank);
                        } else {
                            player.sendMessage(StringUtil.format(config.getString("target-not-found")));
                        }
                    }
                }
            }
        } else {
            player.sendMessage(StringUtil.format(config.getString("no-permissions")));
        }
        return false;
    }
}
