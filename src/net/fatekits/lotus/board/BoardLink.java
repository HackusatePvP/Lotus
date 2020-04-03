package net.fatekits.lotus.board;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.servers.Server;
import net.fatekits.lotus.utils.StringUtil;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class BoardLink implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return StringUtil.format(Lotus.getPlugin().getConfig().getString("scoreboard.title"));
    }

    @Override
    public List<String> getLines(Player player) {
        if (QueueAPI.getQueueManager().inQueue(player)) {
            return getFormattedLines(Lotus.getPlugin().getConfig().getStringList("scoreboard.lines.queue"), player);
        }
        return getFormattedLines(Lotus.getPlugin().getConfig().getStringList("scoreboard.lines.hub"), player);
    }

    public List<String> getFormattedLines(List<String> lines, Player player) {
        List<String> format = new ArrayList<>();
        for (String s : lines) {
            s = StringUtil.format(s);
            if (QueueAPI.getQueueManager().inQueue(player)) {
                s = s.replace("%QUEUE%", QueueAPI.getQueueManager().getQueuePlayer(player).getQueue().getName());
                s = s.replace("%POSITION%", QueueAPI.getQueueManager().getQueuePlayer(player).getPosition() + "");
                s = s.replace("%PRIORITY%", QueueAPI.getQueueManager().getQueuePlayer(player).getPriority() + "");
                s = s.replace("%QUEUESIZE%", QueueAPI.getQueueManager().getQueuePlayer(player).getQueue().getQueuePlayers().size() + "");
            }
            s = s.replace("%ONLINE%", getTotalCount() + "");
            if (Lotus.getPlugin().getRankManager().getRank(player) != null) {
                s = s.replace("%RANK%", Lotus.getPlugin().getRankManager().getRank(player).getName() + "");
            }
            format.add(s);
        }
        return format;
    }

    private int getTotalCount() {
        int count = Bukkit.getOnlinePlayers().size();
        for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("servers").getKeys(false)) {
            Server server = Lotus.getPlugin().getServerManager().getServer(s);
            count = count + server.getOnline();
        }
        return count;
    }
}
