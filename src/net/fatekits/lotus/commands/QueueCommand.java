package net.fatekits.lotus.commands;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.queue.Queue;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class QueueCommand implements CommandExecutor {
   private FileConfiguration config = Lotus.getPlugin().getLangConfig().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String ss, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (args.length == 0) {
            if (!profile.isStaff()) {
                player.sendMessage(StringUtil.format("&c/queue <queue>"));
                return true;
            }
            List<String> message = new ArrayList<>();
            message.add("&7&m------------------------------------");
            message.add("&4%lQueue &7Help");
            message.add("");
            message.add("&6* &7/queue &c<queue>");
            message.add("&6* &7/queue &c<queue> &4<pause>");
            message.add("&6* &7/queue &c<queue> &2<resume>");
            message.add("&7&m------------------------------------");
            message.forEach(msg -> player.sendMessage(StringUtil.format(msg)));
        } else {
            if (args.length == 1) {
                if (Lotus.getPlugin().getQueueManager().getQueues().containsKey(args[0])) {
                    if (!Lotus.getPlugin().getQueueManager().inQueue(player)) {
                        Queue queue = Lotus.getPlugin().getQueueManager().getQueue(args[0]);
                        Lotus.getPlugin().getQueueManager().addToQueue(player, queue);
                        player.sendMessage(format(config.getString("player-queued"), queue));
                    } else {
                        player.sendMessage(StringUtil.format(config.getString("already-in-queue")));
                    }
                } else {
                    player.sendMessage(StringUtil.format(config.getString("queue-not-found")));
                }
            }
            if (args.length == 2) {
                if (profile.isStaff()) {
                    if (Lotus.getPlugin().getQueueManager().getQueues().containsKey(args[0])) {
                        Queue queue = Lotus.getPlugin().getQueueManager().getQueue(args[0]);
                        if (args[1].equalsIgnoreCase("pause")) {
                            player.sendMessage(format(config.getString("queue-set-pause"), queue));
                            queue.setRunning(false);
                        }
                        if (args[1].equalsIgnoreCase("resume")) {
                            player.sendMessage(format(config.getString("queue-set-resume"), queue));
                            queue.setRunning(true);
                        }
                        if (args[1].equalsIgnoreCase("reset")) {
                            player.sendMessage(format(config.getString("queue-connection-reset"), queue));
                            Lotus.getPlugin().getQueueManager().resetConnection(queue);
                        }
                    } else {
                        player.sendMessage(StringUtil.format(config.getString("queue-not-found")));
                    }
                } else {
                    player.sendMessage(StringUtil.format(config.getString("no-permissions")));
                }
            }

        }
        return false;
    }

    public String format(String s, Queue queue) {
        s = StringUtil.format(s);
        s = s.replace("%QUEUE%", queue.getName());
        return s;
    }
}
