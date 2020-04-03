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

public class RankCommand implements CommandExecutor {
    private FileConfiguration config = Lotus.getPlugin().getLangConfig().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage("usage: /rank <player> <rank>");
            } else {
                if (args.length == 1) {
                    sender.sendMessage("usage: /rank <" + args[0] + "> <rank>");
                } else if (args.length == 2) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null) {
                        if (Lotus.getPlugin().getRankManager().getRanks().containsKey(args[1])) {
                            String rank = args[1];
                            Profile tar = Lotus.getPlugin().getProfileManager().getProfile(target.getUniqueId());
                            tar.setRank(rank);
                        } else {
                            sender.sendMessage(StringUtil.format(config.getString("rank-not-found")));
                        }
                    } else {
                        sender.sendMessage(StringUtil.format(config.getString("target-not-found")));
                    }
                }
            }
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("lotus.admin")) {
            player.sendMessage(StringUtil.format(config.getString("no-permissions")));
        }
        if (args.length == 0) {

        } else {
            if (args.length == 1) {
                player.sendMessage("&cUsage: /rank <" + args[0] + "> <rank>");
            } else if (args.length == 2) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null) {
                    if (Lotus.getPlugin().getRankManager().getRanks().containsKey(args[1])) {
                        String rank = args[1];
                        Profile tar = Lotus.getPlugin().getProfileManager().getProfile(target.getUniqueId());
                        tar.setRank(rank);
                        player.sendMessage(StringUtil.format(format(config.getString("rank-set-player"), tar)));
                    } else {
                        player.sendMessage(StringUtil.format(config.getString("rank-not-found")));
                    }
                } else {
                    player.sendMessage(StringUtil.format(config.getString("target-not-found")));
                }
            } else {
                player.sendMessage(StringUtil.format(config.getString("argument-not-found")));
            }
        }
        return false;
    }

    public String format(String s, Profile profile) {
        s = s.replace("%RANK%", profile.getRank());
        s = s.replace("%PLAYER%", profile.getPlayer().getName());
        return s;
    }
}
