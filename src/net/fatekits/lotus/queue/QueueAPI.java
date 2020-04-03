package net.fatekits.lotus.queue;

import net.fatekits.lotus.Lotus;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class QueueAPI {
    @Getter public static QueueManager queueManager;
    private QueueTask queueTask;

    public QueueAPI(JavaPlugin plugin) {
        registerQueue();
        plugin.getLogger().info("loaded QueueAPI...");
    }

    private void registerQueue() {
        queueManager = new QueueManager();
        queueManager.loadQueues();
        queueTask = new QueueTask();
        queueTask.runTaskTimer(Lotus.getPlugin(), 0, 20);
    }
}
