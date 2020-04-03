package net.fatekits.lotus.commands;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.queue.Queue;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.queue.QueuePlayer;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LeaveQueueCommand implements CommandExecutor {
    private FileConfiguration config = Lotus.getPlugin().getLangConfig().getConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (QueueAPI.getQueueManager().inQueue(player)) {
            Queue queue = QueueAPI.getQueueManager().getQueue(player);
            QueuePlayer queuePlayer = QueueAPI.getQueueManager().getQueuePlayer(player);
            queue.getQueuePlayers().remove(queuePlayer);
            QueueAPI.getQueueManager().getPlayerInQueue().remove(player);
            QueueAPI.getQueueManager().getQueuePlayers().remove(queuePlayer);
            QueueAPI.getQueueManager().getInQueue().remove(queuePlayer);
            player.sendMessage(StringUtil.format(config.getString("leave-queue")));
        } else {
            player.sendMessage(StringUtil.format(config.getString("not-in-queue")));
        }
        return false;
    }
}
