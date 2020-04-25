package net.fatekits.lotus.queue;

import net.fatekits.lotus.Lotus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QueueListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Lotus.getPlugin().getQueueManager().inQueue(player)) {
            Queue queue = Lotus.getPlugin().getQueueManager().getQueue(player);
            QueuePlayer queuePlayer = Lotus.getPlugin().getQueueManager().getQueuePlayer(player);
            queue.getQueuePlayers().remove(queuePlayer);
            Lotus.getPlugin().getQueueManager().getPlayerInQueue().remove(player);
            Lotus.getPlugin().getQueueManager().getQueuePlayers().remove(queuePlayer);
            Lotus.getPlugin().getQueueManager().getInQueue().remove(queuePlayer);
        }
    }
}
