package net.fatekits.lotus.queue;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QueueListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (QueueAPI.getQueueManager().inQueue(player)) {
            Queue queue = QueueAPI.getQueueManager().getQueue(player);
            QueuePlayer queuePlayer = QueueAPI.getQueueManager().getQueuePlayer(player);
            queue.getQueuePlayers().remove(queuePlayer);
            QueueAPI.getQueueManager().getPlayerInQueue().remove(player);
            QueueAPI.getQueueManager().getQueuePlayers().remove(queuePlayer);
            QueueAPI.getQueueManager().getInQueue().remove(queuePlayer);
        }
    }
}
