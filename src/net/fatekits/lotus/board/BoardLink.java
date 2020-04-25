package net.fatekits.lotus.board;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.queue.Queue;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.servers.Server;
import net.fatekits.lotus.servers.ServerAPI;
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
        if (Lotus.getPlugin().getQueueManager().inQueue(player)) {
            return getFormattedLines(Lotus.getPlugin().getConfig().getStringList("scoreboard.lines.queue"), player);
        }
        return getFormattedLines(Lotus.getPlugin().getConfig().getStringList("scoreboard.lines.hub"), player);
    }

    public List<String> getFormattedLines(List<String> lines, Player player) {
        List<String> format = new ArrayList<>();
        for (String s : lines) {
            s = StringUtil.format(s);
            s = s.replace("%PLAYER%", player.getName());
            if (Lotus.getPlugin().getQueueManager().inQueue(player)) {
                s = s.replace("%QUEUE%", Lotus.getPlugin().getQueueManager().getQueuePlayer(player).getQueue().getName());
                s = s.replace("%POSITION%", Lotus.getPlugin().getQueueManager().getQueuePlayer(player).getPosition() + "");
                s = s.replace("%PRIORITY%", Lotus.getPlugin().getQueueManager().getQueuePlayer(player).getPriority() + "");
                s = s.replace("%QUEUESIZE%", Lotus.getPlugin().getQueueManager().getQueuePlayer(player).getQueue().getQueuePlayers().size() + "");
            }
            s = s.replace("%ONLINE%", ServerAPI.getTotalCount() + "");
            if (Lotus.getPlugin().getRankManager().getRank(player) != null) {
                s = s.replace("%RANK%", Lotus.getPlugin().getRankManager().getRank(player).getName() + "");
            }
            for (Server server : Lotus.getPlugin().getServerManager().getServers().values()) {
                if (server != null) {
                    s = s.replace("%" + server.getName().toUpperCase() + "%", server.getName());
                    s = s.replace("%" + server.getName().toUpperCase() + "ONLINE%", server.getOnline() + "");
                    if (Lotus.getPlugin().getQueueManager().getQueue(server.getName()) != null) {
                        s = s.replace("%" + server.getName().toUpperCase() + "QUEUE%", Lotus.getPlugin().getQueueManager().getQueue(server.getName()).getQueuePlayers().size() + "");
                    }
                }
            }
            format.add(s);
        }
        return format;
    }
}
