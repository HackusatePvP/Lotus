package net.fatekits.lotus.queue;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import java.util.UUID;

@Getter
@Setter
public class QueuePlayer {
    private UUID uuid;
    private Player player;
    private int position;
    private int priority;
    private Queue queue;

    public QueuePlayer(UUID uuid, Player player, int priority, Queue queue) {
        this.uuid = uuid;
        this.player = player;
        this.priority = priority;
        this.queue = queue;
    }
}
