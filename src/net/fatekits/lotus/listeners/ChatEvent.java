package net.fatekits.lotus.listeners;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.ranks.Rank;
import net.fatekits.lotus.ranks.RankAPI;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        event.setCancelled(true);
        if (!profile.isChat()) {
            player.sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("chat-off")));
            return;
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            Profile onpro = Lotus.getPlugin().getProfileManager().getProfile(online.getUniqueId());
            if (onpro.isChat()) {
                online.sendMessage(StringUtil.format(format(Lotus.getPlugin().getLangConfig().getConfig().getString("chat-format"), player, event.getMessage())));
            }
        }
    }

    private String format(String s, Player player, String message) {
        s = s.replace("%PREFIX%", RankAPI.getRank(player).getPrefix());
        s = s.replace("%COLOR%", Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId()).getColor());
        s = s.replace("%PLAYER%", player.getName());
        s = s.replace("%MESSAGE%", message);
        return s;
    }
}
