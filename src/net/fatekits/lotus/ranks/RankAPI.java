package net.fatekits.lotus.ranks;

import net.fatekits.lotus.Lotus;
import org.bukkit.entity.Player;

public class RankAPI {

    public static Rank getRank(String rank) {
        for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("ranks").getKeys(false)) {
            return Lotus.getPlugin().getRankManager().getRank(s);
        }
        return null;
    }

    public static Rank getRank(Player player) {
        return Lotus.getPlugin().getRankManager().getRank(player);
    }

    public static void setRankPlayer(Player player, String rank) {
        if (Lotus.getPlugin().getConfig().getConfigurationSection("ranks").getKeys(false).contains(rank)) {
            Lotus.getPlugin().getRankManager().addRankPermissions(player, rank);
        }
    }
}
