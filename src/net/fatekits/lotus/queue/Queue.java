package net.fatekits.lotus.queue;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class Queue {
    private String name;
    private int positions,maxplayers;
    private Map<QueuePlayer, Integer> queuePlayers;
    private boolean running;

    public Queue(String name, Map<QueuePlayer, Integer> queuePlayers) {
        this.name = name;
        this.queuePlayers = queuePlayers;
    }

}
