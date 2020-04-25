package net.fatekits.lotus.queue;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.utils.StringUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class QueueManager {
    @Getter private Map<String, Queue> queues = new HashMap<>();
    @Getter private Map<QueuePlayer, Queue> inQueue = new HashMap<>();
    @Getter private Set<QueuePlayer> queuePlayers = new HashSet<>();
    @Getter private Set<Player> playerInQueue = new HashSet<>();
    private Map<Player, QueuePlayer> qplayers = new HashMap<>();
    private Set<QueuePlayer> messageDonor = new HashSet<>();
    private Set<QueuePlayer> messageAd = new HashSet<>();
    private Set<Queue> connection = new HashSet<>();


    public Queue getQueue(String name) {
        if (queues.containsKey(name)) {
            return queues.get(name);
        }
        return null;
    }

    public Queue getQueue(Player player) {
        if (qplayers.containsKey(player)) {
            QueuePlayer queuePlayer = qplayers.get(player);
            if (inQueue.containsKey(queuePlayer)) {
                return queuePlayer.getQueue();
            }
        } else {
            return null;
        }
        return null;
    }

    public Queue getQueue(QueuePlayer queuePlayer) {
        return queuePlayer.getQueue();
    }

    public boolean inQueue(Player player) {
        if (playerInQueue.contains(player)) {
            return true;
        } else if (qplayers.containsKey(player)) {
            QueuePlayer queuePlayer = qplayers.get(player);
            if (inQueue.containsKey(queuePlayer)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public QueuePlayer getQueuePlayer(Player player) {
        if (qplayers.containsKey(player)) {
            return qplayers.get(player);
        }
        return null;
    }
    public void resetConnection(Queue queue) {
        connection.remove(queue);
    }

    public int getPosition(QueuePlayer queuePlayer) {
        return queuePlayer.getPosition();
    }

    public void removePlayerFromQueue(Player player) {
        QueuePlayer queuePlayer = getQueuePlayer(player);
        inQueue.remove(queuePlayer);
        queuePlayers.remove(queuePlayer);
        qplayers.remove(player);
        playerInQueue.remove(player);
    }

    public void loadQueues() {
        for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("queues").getKeys(false)) {
            FileConfiguration config = Lotus.getPlugin().getConfig();
            String path = "queues." + s;
            Bukkit.getLogger().info("[queue] added " + s + " with " + s + ": " + config.getString(path + ".name"));
            Queue queue = new Queue(config.getString(path + ".name"), new HashMap<>());
            queue.setName(config.getString(path + ".name"));
            queue.setMaxplayers(Lotus.getPlugin().getConfig().getInt(s + ".maxplayers"));
            queue.setRunning(config.getBoolean(s + ".paused"));
            queues.put(queue.getName(), queue);

        }
    }

    public void addToQueue(Player player, Queue queue) {
        if (!playerInQueue.contains(player)) {
            QueuePlayer queuePlayer = new QueuePlayer(player.getUniqueId(), player, Lotus.getPlugin().getRankManager().getRank(player).getLadder(), queue);
            qplayers.put(player, queuePlayer);
            playerInQueue.add(player);
            queuePlayer.setQueue(queue);
            queuePlayers.add(queuePlayer);
            inQueue.put(queuePlayer, queue);
            for (QueuePlayer inQueue : queuePlayers) {
                if (inQueue(inQueue.getPlayer())) {
                    if (!inQueue.getPlayer().getName().equalsIgnoreCase(queuePlayer.getPlayer().getName())) {
                        if (queuePlayers.size() > 1) {
                            if (inQueue.getQueue() != null) {
                                if (inQueue.getQueue().getName().equalsIgnoreCase(queuePlayer.getQueue().getName())) {
                                    if (queuePlayer.getPlayer().hasPermission("lotus.queue.bypass") || player.isOp()) {
                                        String command = "send " + player.getName() + " " + queue.getName();
                                        sendPlayer(queuePlayer.getPlayer(), "get", command, queue);
                                        player.sendMessage(StringUtil.format("&7Because of your rank you have bypassed the queue."));
                                        return;
                                    }
                                    if (queuePlayer.getPriority() > inQueue.getPriority()) {
                                        //queue player > inqueue
                                        Bukkit.getLogger().info("[Queue] 1");
                                        queuePlayer.setPosition(inQueue.getPosition());
                                        inQueue.setPosition(queuePlayer.getPosition() + 1);
                                        queue.getQueuePlayers().remove(inQueue);
                                        queue.getQueuePlayers().put(inQueue, getPosition(inQueue));
                                        queue.getQueuePlayers().put(queuePlayer, getPosition(queuePlayer));
                                        inQueue.getPlayer().sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-non-donor")));
                                        messageDonor.add(queuePlayer);
                                    } else {
                                        Bukkit.getLogger().info("[Queue] 2");
                                        queue.getQueuePlayers().put(queuePlayer, queue.getQueuePlayers().size() + 1);
                                        queuePlayer.setPosition(queuePlayers.size());
                                        queuePlayers.add(queuePlayer);
                                        messageAd.add(queuePlayer);
                                    }
                                    queuePlayers.add(queuePlayer);
                                }
                            }
                        } else {
                            if (queuePlayer.getPlayer().hasPermission("lotus.queue.bypass") || player.isOp()) {
                                String command = "send " + player.getName() + " " + queue.getName();
                                sendPlayer(queuePlayer.getPlayer(), "get", command, queue);
                                return;
                            }
                            Bukkit.getLogger().info("[Queue] 3");
                            queuePlayer.setPosition(1);
                            queue.getQueuePlayers().put(queuePlayer, 1);
                            queuePlayers.add(queuePlayer);
                        }
                    } else {
                        if (!(queue.getQueuePlayers().size() > 1)) {
                            if (!queue.getQueuePlayers().containsKey(queuePlayer)) {
                                if (queuePlayer.getPlayer().hasPermission("lotus.queue.bypass") || player.isOp()) {
                                    String command = "send " + player.getName() + " " + queue.getName();
                                    sendPlayer(queuePlayer.getPlayer(), "get", command, queue);
                                    return;
                                }
                                queue.getQueuePlayers().put(inQueue, 1);
                                queuePlayer.setPosition(1);
                                queuePlayers.add(queuePlayer);
                            } else {
                                queue.getQueuePlayers().remove(queuePlayer);
                            }
                        } else {
                            if (inQueue.getPriority() > queuePlayer.getPriority()) {
                                int pos = queue.getQueuePlayers().get(inQueue);
                                //queue player > inqueue
                                queuePlayer.setPosition(inQueue.getPosition());
                                inQueue.setPosition(queuePlayer.getPosition() + 1);
                                queue.getQueuePlayers().remove(inQueue);
                                queue.getQueuePlayers().put(inQueue, getPosition(inQueue));
                                queue.getQueuePlayers().put(queuePlayer, getPosition(queuePlayer));
                                inQueue.getPlayer().sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-non-donor")));
                                messageDonor.add(queuePlayer);
                            } else {
                                queue.getQueuePlayers().put(queuePlayer, queue.getQueuePlayers().size() + 1);
                                queuePlayer.setPosition(queuePlayers.size());
                                messageAd.add(queuePlayer);
                            }
                            queuePlayers.add(queuePlayer);
                        }
                    }
                }
                if (messageDonor.contains(queuePlayer)) {
                    queuePlayer.getPlayer().sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-rank-message")));
                }
                if (messageAd.contains(queuePlayer)) {
                    queuePlayer.getPlayer().sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-advertisement-message")));
                }
            }
        }
    }

    void sendPlayers(Queue queue) {
        if (queue != null) {
            if (queue.getQueuePlayers().entrySet().stream().findFirst().isPresent()) {
                QueuePlayer q = queue.getQueuePlayers().entrySet().stream().findFirst().get().getKey();
                Player player = q.getPlayer();
                String command = "send " + player.getName() + " " + queue.getName();
                Bukkit.getLogger().info("[queues] sending players to " + queue.getName() + ": " + q.getPlayer().getName());
                this.queuePlayers.remove(q);
                this.inQueue.remove(q);
                this.playerInQueue.remove(player);
                queue.getQueuePlayers().remove(q);
                qplayers.remove(player);
                sendPlayer(player, "get", command, queue);
                for (QueuePlayer queuePlayer : queue.getQueuePlayers().keySet()) {
                    queuePlayer.setPosition(queuePlayer.getPosition() - 1);
                }
                Bukkit.getLogger().info("[command]: /send " + player.getName() + " " + queue.getName());
            }
        }
    }

     void updatePositions(Queue queue) {
        if (queue != null) {
            for (QueuePlayer inQueue : queuePlayers) {
                if (queue.getQueuePlayers().size() != 0) {
                    if (inQueue.getQueue().getName().equals(queue.getName())) {
                        if (queue.getQueuePlayers().entrySet().stream().findFirst().isPresent()) {
                            QueuePlayer q = queue.getQueuePlayers().entrySet().stream().findFirst().get().getKey();
                            if (inQueue.getPriority() > q.getPriority()) {
                                inQueue.setPosition(q.getPosition());
                                queue.getQueuePlayers().remove(inQueue);
                                queue.getQueuePlayers().remove(q);
                                queue.setPositions(q.getPosition() + 1);
                                queue.getQueuePlayers().put(q, q.getPosition());
                                Bukkit.getLogger().info("[Queue] updating positions for " + q.getPlayer().getName() + "," + inQueue.getPlayer().getName());
                                q.getPlayer().sendMessage(StringUtil.format("&7You position is now &9" + getPosition(q)));
                                inQueue.getPlayer().sendMessage(StringUtil.format("&7Your position is now &9" + getPosition(inQueue)));
                            }
                        }
                    }
                }
            }
        }
    }


    void sendPlayer(Player p, String channel, String command, Queue queue){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF(channel);
            out.writeUTF(command);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[Queue] Target server is not online, please make sure you have typed the ip correctly and the server is online.");
            connection.add(queue);
            removePlayerFromQueue(p);
        }
        p.sendPluginMessage(Lotus.getPlugin(Lotus.class), "BungeeCord", b.toByteArray());
    }
}
