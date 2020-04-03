package net.fatekits.lotus.commands;

import net.fatekits.lotus.queue.Queue;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class QueuesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        List<String> message = new ArrayList<>();
        Player player = (Player) sender;
        message.add("&7&m----------------------------");
        message.add("&9Queues: ");
        message.add("");
        for (Queue queue : QueueAPI.getQueueManager().getQueues().values()) {
            message.add("&b* &7" + queue.getName() + ": &9" + queue.getQueuePlayers().size());
        }
        message.add("&7&m----------------------------");
        message.forEach(msg -> player.sendMessage(StringUtil.format(msg)));
        return false;
    }
}
