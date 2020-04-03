package net.fatekits.lotus.profiles;

import net.fatekits.lotus.Lotus;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class ProfileManager {
    private final String INSERT = "INSERT INTO profiles VALUES(?,?,?,?,?,?,?,?,?,?)";
    @Getter
    Lotus instance;

    private Map<UUID, Profile> profiles = new HashMap<>();

    public void addProfile(UUID uuid, Profile profile) {
        profiles.put(uuid, profile);
    }

    public Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public boolean hasProfile(UUID uuid) {
        return profiles.containsKey(uuid);
    }

    public void load(Player player) {
        Profile playerProfile = new Profile(player.getUniqueId());
        profiles.put(player.getUniqueId(), playerProfile);
        addProfile(player.getUniqueId(), playerProfile);
        final String SELECT = "SELECT NAME, IP, SCOREBOARD, TABLIST, CHAT, VISIBILITY, RANK, COLOR, STAFF FROM profiles WHERE UUID =?";
        Bukkit.getScheduler().runTaskAsynchronously(Lotus.getPlugin(), () -> {
            try {
                PreparedStatement preparedStatement = Lotus.getPlugin().getMySQL().getConnection().prepareStatement(SELECT);
                preparedStatement.setString(1, player.getUniqueId().toString());

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    playerProfile.setName(rs.getString("NAME"));
                    playerProfile.setIP(rs.getString("IP"));
                    playerProfile.setScoreboard(rs.getBoolean("SCOREBOARD"));
                    playerProfile.setTablist(rs.getBoolean("TABLIST"));
                    playerProfile.setChat(rs.getBoolean("CHAT"));
                    playerProfile.setVisibility(rs.getBoolean("VISIBILITY"));
                    playerProfile.setRank(rs.getString("RANK"));
                    playerProfile.setColor(rs.getString("COLOR"));
                    playerProfile.setStaff(rs.getBoolean("STAFF"));
                    for (String permission : Lotus.getPlugin().getRankManager().getRank(playerProfile.getRank()).getPermissions()) {
                        Lotus.getPlugin().getRankManager().addPermissions(player, permission);
                    }
                }
            } catch (SQLException e) {
                Bukkit.getLogger().log(Level.SEVERE, e.toString());
            }

            player.getInventory().setContents(Lotus.getPlugin().getItemsInventory().getItemsInventory(player).getContents());
        });
    }

    public void unload(Player player) {
        Profile profile = getProfile(player.getUniqueId());

        try {
            PreparedStatement preparedStatement = Lotus.getPlugin().getMySQL().getConnection().prepareStatement("UPDATE profiles SET NAME = ?, IP = ?, SCOREBOARD = ?, TABLIST = ?, CHAT = ?, VISIBILITY = ?, RANK = ?, COLOR = ?, STAFF = ? WHERE UUID = ?");
            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, player.getAddress().getAddress().getHostAddress());
            preparedStatement.setBoolean(3, profile.isScoreboard());
            preparedStatement.setBoolean(4, profile.isTablist());
            preparedStatement.setBoolean(5, profile.isChat());
            preparedStatement.setBoolean(6, profile.isVisibility());
            preparedStatement.setString(7, profile.getRank());
            preparedStatement.setString(8, profile.getColor());
            preparedStatement.setBoolean(9, profile.isStaff());
            // last
            preparedStatement.setString(10, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
            PermissionAttachment attachment = player.addAttachment(Lotus.getPlugin());
            player.removeAttachment(attachment);
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, e.toString());
        }
    }

    public boolean existUser(Player player) {
        PreparedStatement ps;
        try {
            ps = Lotus.getPlugin().getMySQL().getConnection().prepareStatement("SELECT * FROM profiles WHERE UUID = ?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, e.toString());
            return false;
        }
    }

    public static void userExists(final Callback<HashMap<String, Boolean>> callback,
                                  final String uuid) {
        // Create a async Bukkit scheduler
        Bukkit.getScheduler().runTaskAsynchronously(Lotus.getPlugin(), () -> {
            final HashMap<String, Boolean> result = new HashMap<>();
            PreparedStatement ps;

            try {
                ps = Lotus.getPlugin().getMySQL().getConnection().prepareStatement("SELECT * FROM profiles WHERE UUID = ?");
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                result.put(uuid, rs.next());

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Create a new async Bukkit scheduler
            Bukkit.getScheduler().runTaskAsynchronously(Lotus.getPlugin(), () -> {
                try {
                    callback.onSuccess(result);
                } catch (Exception ex) {
                    callback.onFailure(ex.getCause());
                }
            });
        });
    }

    public interface Callback<T> {
        void onSuccess(T done);

        void onFailure(Throwable cause);
    }

    public void createUser(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Lotus.getPlugin(), () -> {
            try {
                PreparedStatement preparedStatement = Lotus.getPlugin().getMySQL().getConnection().prepareStatement(INSERT + " ON DUPLICATE KEY UPDATE UUID='" + player.getUniqueId() + "'");
                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.setString(2, player.getName());
                preparedStatement.setString(3, player.getAddress().getAddress().getHostAddress());
                preparedStatement.setBoolean(4, true);
                preparedStatement.setBoolean(5, true);
                preparedStatement.setBoolean(6, true);
                preparedStatement.setBoolean(7, true);
                preparedStatement.setString(8, "Default");
                preparedStatement.setString(9, "&7");
                preparedStatement.setBoolean(10, false);
                preparedStatement.execute();
                preparedStatement.executeUpdate();
                Lotus.getPlugin().getProfileManager().load(player);
            } catch (SQLException e) {
                Bukkit.getLogger().log(Level.SEVERE, e.toString());
            }
        });
    }
}
