package net.fatekits.lotus.queue;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class QueueTask extends BukkitRunnable {
    private int cool = 0;

    @Override
    public void run() {
        cool++;
        if (cool == 3) {
            for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("queues").getKeys(false)) {
                FileConfiguration config = Lotus.getPlugin().getConfig();
                String path = "queues." + s;
                Queue queue = QueueAPI.getQueueManager().getQueue(s);
             //   Bukkit.getLogger().info("[Queues] sending and updating for " + s);
                if (queue.isRunning()) {
                    if (queue.getQueuePlayers().size() == queue.getMaxplayers()) {
                        if (queue.getQueuePlayers().size() != 0) {
                            QueueAPI.getQueueManager().sendPlayers(queue);
                        }

                        QueueAPI.getQueueManager().updatePositions(queue);
                    } else {
                        queue.getQueuePlayers().keySet().forEach(qplayer -> qplayer.getPlayer().sendMessage(format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-position-message"), qplayer.getPlayer())));
                        QueueAPI.getQueueManager().updatePositions(queue);
                    }
                } else {
                    queue.getQueuePlayers().keySet().forEach(qplayer -> qplayer.getPlayer().sendMessage(format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-position-message"), qplayer.getPlayer())));
                    QueueAPI.getQueueManager().updatePositions(queue);
                }
            }
            cool = 0;
        }
    }


    public String format(String s, Player player) {
        s = StringUtil.format(s);
        if (QueueAPI.getQueueManager().inQueue(player)) {
            QueuePlayer queuePlayer = QueueAPI.getQueueManager().getQueuePlayer(player);
            s = s.replace("%QUEUE%", queuePlayer.getQueue().getName());
            s = s.replace("%POSITION%", queuePlayer.getPosition() + "");
            s = s.replace("%QUEUESIZE%", QueueAPI.getQueueManager().getQueuePlayer(player).getQueue().getQueuePlayers().size() + "");
        }
        return s;
    }
}
