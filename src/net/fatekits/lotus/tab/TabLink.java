package net.fatekits.lotus.tab;


import com.bizarrealex.azazel.tab.TabAdapter;
import com.bizarrealex.azazel.tab.TabTemplate;
import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.queue.Queue;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.ranks.RankAPI;
import net.fatekits.lotus.servers.Server;
import net.fatekits.lotus.servers.ServerAPI;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TabLink implements TabAdapter {
    FileConfiguration config = Lotus.getPlugin().getTabConfig().getConfig();

    public TabTemplate getTemplate(Player player) {
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (!profile.isTablist()) {
            return null;
        }
        TabTemplate template = new TabTemplate();
        template.left(0, format(config.getString("tab.1"), player));
        template.middle(0, format(config.getString("tab.2"), player));
        template.right(0, format(config.getString("tab.3"), player));

        template.left(1, format(config.getString("tab.4"), player));
        template.middle(1, format(config.getString("tab.5"), player));
        template.right(1, format(config.getString("tab.6"), player));

        template.left(2, format(config.getString("tab.7"), player));
        template.middle(2, format(config.getString("tab.8"), player));
        template.right(2, format(config.getString("tab.9"), player));

        template.left(3, format(config.getString("tab.10"), player));
        template.middle(3, format(config.getString("tab.11"), player));
        template.right(3, format(config.getString("tab.12"), player));

        template.left(4, format(config.getString("tab.13"), player));
        template.middle(4, format(config.getString("tab.14"), player));
        template.right(4, format(config.getString("tab.15"), player));

        template.left(5, format(config.getString("tab.16"), player));
        template.middle(5, format(config.getString("tab.17"), player));
        template.right(5, format(config.getString("tab.18"), player));

        template.left(6, format(config.getString("tab.19"), player));
        template.middle(6, format(config.getString("tab.20"), player));
        template.right(6, format(config.getString("tab.21"), player));

        template.left(7, format(config.getString("tab.22"), player));
        template.middle(7, format(config.getString("tab.23"), player));
        template.right(7, format(config.getString("tab.24"), player));

        template.left(8, format(config.getString("tab.25"), player));
        template.middle(8, format(config.getString("tab.26"), player));
        template.right(8, format(config.getString("tab.27"), player));

        template.left(9, format(config.getString("tab.28"), player));
        template.middle(9, format(config.getString("tab.29"), player));
        template.right(9, format(config.getString("tab.30"), player));

        template.left(10, format(config.getString("tab.31"), player));
        template.middle(10, format(config.getString("tab.32"), player));
        template.right(10, format(config.getString("tab.33"), player));

        template.left(11, format(config.getString("tab.34"), player));
        template.middle(11, format(config.getString("tab.35"), player));
        template.right(11, format(config.getString("tab.36"), player));

        template.left(12, format(config.getString("tab.37"), player));
        template.middle(12, format(config.getString("tab.38"), player));
        template.right(12, format(config.getString("tab.39"), player));

        template.left(13, format(config.getString("tab.40"), player));
        template.middle(13, format(config.getString("tab.41"), player));
        template.right(13, format(config.getString("tab.42"), player));

        template.left(13, format(config.getString("tab.41"), player));
        template.middle(13, format(config.getString("tab.42"), player));
        template.right(13, format(config.getString("tab.43"), player));

        template.left(14, format(config.getString("tab.44"), player));
        template.middle(14, format(config.getString("tab.45"), player));
        template.right(14, format(config.getString("tab.46"), player));

        template.left(15, format(config.getString("tab.47"), player));
        template.middle(15, format(config.getString("tab.48"), player));
        template.right(15, format(config.getString("tab.49"), player));

        template.left(16, format(config.getString("tab.50"), player));
        template.middle(16, format(config.getString("tab.51"), player));
        template.right(16, format(config.getString("tab.52"), player));

        template.left(17, format(config.getString("tab.53"), player));
        template.middle(17, format(config.getString("tab.54"), player));
        template.right(17, format(config.getString("tab.55"), player));

        template.left(18, format(config.getString("tab.56"), player));
        template.middle(18, format(config.getString("tab.57"), player));
        template.right(18, format(config.getString("tab.58"), player));

        template.left(19, format(config.getString("tab.59"), player));
        template.middle(19, format(config.getString("tab.60"), player));
        template.right(19, format(config.getString("tab.61"), player));

        return template;
    }

    public String format(String s, Player player) {
        s = StringUtil.format(s);
        s = s.replace("%PLAYER%", player.getName());
        s = s.replace("%ONLINE%", ServerAPI.getTotalCount() + "");
        s = s.replace("%RANK%", RankAPI.getRank(player).getName());
        for (Server server : Lotus.getPlugin().getServerManager().getServers().values()) {
            s = s.replace("%" + server.getName().toUpperCase() + "%", server.getName());
            s = s.replace("%" + server.getName().toUpperCase() + "ONLINE%", server.getOnline() + "");
            if (Lotus.getPlugin().getQueueManager().getQueue(server.getName()) != null) {
                Queue queue = Lotus.getPlugin().getQueueManager().getQueue(server.getName());
                s = s.replace("%" + server.getName().toUpperCase() + "QUEUE%", queue.getQueuePlayers().size() + "");
            }
            //todo server status
        }
        return s;
    }
}
