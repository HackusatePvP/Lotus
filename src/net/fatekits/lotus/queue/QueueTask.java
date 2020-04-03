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
        if (cool == 5) {
            for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("queues").getKeys(false)) {
                FileConfiguration config = Lotus.getPlugin().getConfig();
                String path = "queues." + s;
                Bukkit.getLogger().info("[Queues] sending and updating for " + s);
                if (QueueAPI.getQueueManager().getQueue(config.getString(path + ".name")).isRunning()) {
                    if (QueueAPI.getQueueManager().getQueue(config.getString(path + ".name")).getQueuePlayers().size() != 0) {
                        QueueAPI.getQueueManager().sendPlayers(QueueAPI.getQueueManager().getQueue(config.getString(path + ".name")));
                    }
                    QueueAPI.getQueueManager().updatePositions(QueueAPI.getQueueManager().getQueue(config.getString(path + ".name")));
                } else {
                    QueueAPI.getQueueManager().getQueuePlayers().forEach(qplayer -> qplayer.getPlayer().sendMessage(format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-position-message"), qplayer.getPlayer())));
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
