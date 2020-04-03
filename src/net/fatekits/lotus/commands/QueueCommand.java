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
            player.sendMessage(StringUtil.format("&c/queue <queue>"));
        } else {
            if (args.length == 1) {
                if (QueueAPI.getQueueManager().getQueues().containsKey(args[0])) {
                    if (!QueueAPI.getQueueManager().inQueue(player)) {
                        Queue queue = QueueAPI.getQueueManager().getQueue(args[0]);
                        QueueAPI.getQueueManager().addToQueue(player, queue);
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
                    if (QueueAPI.getQueueManager().getQueues().containsKey(args[0])) {
                        Queue queue = QueueAPI.getQueueManager().getQueue(args[0]);
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
                            QueueAPI.getQueueManager().resetConnection(queue);
                        }
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
