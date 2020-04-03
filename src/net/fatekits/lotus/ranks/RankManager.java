package net.fatekits.lotus.ranks;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class RankManager {
   @Getter private HashMap<String, Rank> ranks = new HashMap<>();
   @Getter private HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public Rank getRank(String name) {
        if (ranks.containsKey(name)) {
            return ranks.get(name);
        }
        return null;
    }

    public Rank getRank(Player player) {
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        return getRank(profile.getRank());
    }

    public void loadRanks() {
        int count = 0;
        for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("ranks").getKeys(false)) {
            String path = "ranks." + s;
            Rank rank = new Rank(Lotus.getPlugin().getConfig().getString(path + ".name"));
            ranks.put(rank.getName(), rank);
            rank.setPrefix(Lotus.getPlugin().getConfig().getString(path + ".prefix"));
            rank.setLadder(Lotus.getPlugin().getConfig().getInt(path + ".ladder"));
            rank.setPermissions(Lotus.getPlugin().getConfig().getStringList(path + ".permissions"));
            if (!s.equals("Default")) {
                count++;
            }
            if (count == Lotus.getPlugin().getConfig().getConfigurationSection("ranks").getKeys(false).size()) {
                Lotus.getPlugin().getLog().severe("[Lotus] Default rank not found! Please define \"Default:\" in your ranks configuration.");
                Lotus.getPlugin().getLog().severe("[Lotus] Default rank not found! Please define \"Default:\" in your ranks configuration.");
                Lotus.getPlugin().getLog().severe("[Lotus] Default rank not found! Please define \"Default:\" in your ranks configuration.");
                Lotus.getPlugin().getLog().severe("[Lotus] Default rank not found! Please define \"Default:\" in your ranks configuration.");
                Lotus.getPlugin().getPluginLoader().disablePlugin(Lotus.getPlugin());
            }
        }
    }

    public void addRankPermissions(Player player, String rank) {
        if (rank.contains(rank)) {
            PermissionAttachment attachment = player.addAttachment(Lotus.getPlugin());
            player.addAttachment(Lotus.getPlugin());
            for (String perm : Lotus.getPlugin().getConfig().getStringList("ranks." + rank + ".permissions")) {
                attachment.setPermission(perm, true);
            }
        }
    }

    public void addPermissions(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(Lotus.getPlugin());
        attachment.setPermission(permission, true);
    }
}
