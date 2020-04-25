package net.fatekits.lotus.queue;
import lombok.Getter;
import net.fatekits.lotus.Lotus;
import org.bukkit.entity.Player;

@Getter
public class QueueAPI {

    public static QueuePlayer getQueuePlayer(Player player) {
        return Lotus.getPlugin().getQueueManager().getQueuePlayer(player);
    }

    public static Queue getQueue(String name) {
        return Lotus.getPlugin().getQueueManager().getQueue(name);
    }

    public static Queue getQueue(Player player) {
        return Lotus.getPlugin().getQueueManager().getQueue(player);
    }

    public static Queue getQueue(QueuePlayer queuePlayer) {
        return Lotus.getPlugin().getQueueManager().getQueue(queuePlayer);
    }

    public int getQueueSize(Queue queue) {
        return queue.getQueuePlayers().size();
    }

    public static int getQueuePositionPlayer(Player player) {
        if (Lotus.getPlugin().getQueueManager().inQueue(player)) {
            return Lotus.getPlugin().getQueueManager().getPosition(Lotus.getPlugin().getQueueManager().getQueuePlayer(player));
        }
        return 0;
    }

    public static int getQueuePositionPlayer(QueuePlayer queuePlayer) {
        return queuePlayer.getPosition();
    }

}
