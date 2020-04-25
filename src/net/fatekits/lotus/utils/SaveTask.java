package net.fatekits.lotus.utils;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveTask extends BukkitRunnable {
    private int left = 0;
    private int count = 0;

    public void run() {
        ++left;
        ++count;
        if (left == 120) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
                try {
                    PreparedStatement preparedStatement = Lotus.getPlugin().getMySQL().getConnection().prepareStatement("UPDATE profiles SET NAME = ?, IP = ?, SCOREBOARD = ?, TABLIST = ?, CHAT = ?, VISIBILITY = ?, RANK = ?, COLOR = ?, STAFF = ?, DOUBLEJUMP = ?, ENDERBUTT = ? WHERE UUID = ?");
                    preparedStatement.setString(1, player.getName());
                    preparedStatement.setString(2, player.getAddress().getAddress().getHostAddress());
                    preparedStatement.setBoolean(3, profile.isScoreboard());
                    preparedStatement.setBoolean(4, profile.isTablist());
                    preparedStatement.setBoolean(5, profile.isChat());
                    preparedStatement.setBoolean(6, profile.isVisibility());
                    preparedStatement.setString(7, profile.getRank());
                    preparedStatement.setString(8, profile.getColor());
                    preparedStatement.setBoolean(9, profile.isStaff());
                    preparedStatement.setBoolean(10, profile.isDoublejump());
                    preparedStatement.setBoolean(11, profile.isEnderbutt());
                    // last
                    preparedStatement.setString(12, player.getUniqueId().toString());
                    preparedStatement.executeUpdate();
                    Bukkit.getLogger().info("[Lotus] Updated all profile entries.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Lotus.getPlugin().saveConfig();
                Lotus.getPlugin().getLangConfig().saveConfig();
                left = 0;
            }
        }
    }
}
