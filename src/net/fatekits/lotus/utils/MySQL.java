package net.fatekits.lotus.utils;

import net.fatekits.lotus.Lotus;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {

    private String host = Lotus.getPlugin().getConfig().getString("mysql.host");
    private String port = Lotus.getPlugin().getConfig().getString("mysql.port");
    private String database = Lotus.getPlugin().getConfig().getString("mysql.database");
    private String username = Lotus.getPlugin().getConfig().getString("mysql.username");
    private String password = Lotus.getPlugin().getConfig().getString("mysql.password");
    private Connection con;
    private ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Getter
    Lotus instance;

    public MySQL(Lotus plugin) {
        this.instance = plugin;
    }

    // connect
    public void profiles() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
                console.sendMessage(StringUtil.format("&7A secure connection to the MySQL has &asuccessfully &7been established."));
                PreparedStatement profiles = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS profiles (UUID VARCHAR(36)," +
                        "NAME TEXT, IP TEXT, SCOREBOARD BOOLEAN NOT NULL DEFAULT FALSE, TABLIST BOOLEAN NOT NULL DEFAULT FALSE, CHAT BOOLEAN NOT NULL DEFAULT FALSE, VISIBILITY BOOLEAN NOT NULL DEFAULT FALSE, " +
                        "RANK TEXT, COLOR TEXT, STAFF BOOLEAN NOT NULL DEFAULT FALSE," +
                        "PRIMARY KEY (UUID))");
                profiles.executeUpdate();
            } catch (SQLException e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        }
    }


    // disconnect
    public void disconnectProfiles() {
        if (isConnected()) {
            try {
                con.close();
                console.sendMessage(StringUtil.format("&7The secure connection to MySQL has &csuccessfully &7been closed."));
            } catch (SQLException e) {
                Bukkit.getLogger().info(e.getMessage());
            }
        }
    }


    // isConnected
    public boolean isConnected() {
        return (con != null);
    }

    // getConnection
    public Connection getConnection() {
        return con;
    }
}