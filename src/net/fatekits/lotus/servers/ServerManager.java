package net.fatekits.lotus.servers;

import net.fatekits.lotus.Lotus;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ServerManager {
    @Getter private Map<String, Server> servers = new HashMap<>();

    public Server getServer(String name) {
        return servers.get(name);
    }

    public int getServerOnlineCount(String ip, int port, Server server) {
        try {
            Socket sock = new Socket(ip, port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());

            out.write(0xFE);

            int b;
            StringBuffer str = new StringBuffer();
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    // Not sure what use the two characters are so I omit them
                    str.append((char) b);
                }
            }
            String[] data = str.toString().split("§");
            int onlinePlayers = Integer.parseInt(data[1]);
            server.setOnline(onlinePlayers);
            return onlinePlayers;
        } catch (UnknownHostException e) {
            Lotus.getPlugin().getLogger().info(server.getName() + ": Unknown host.");
            return 0;
        } catch (IOException e) {
            Lotus.getPlugin().getLogger().info(server.getName() + ": connection refused.");
            return 0;
        }
    }

    public void loadServers() {
        for (String server : Lotus.getPlugin().getConfig().getConfigurationSection("servers").getKeys(false)) {
            FileConfiguration config = Lotus.getPlugin().getConfig();
            String path = "servers." + server;
            Server s = new Server(server);
            s.setIp(config.getString(path + ".ip"));
            s.setPort(config.getInt(path + ".port"));
            servers.put(server, s);
        }
    }

    void getOnlineCount(String ip, int port, Server server) {
        try {
            Socket sock = new Socket(ip, port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());

            out.write(0xFE);

            int b;
            StringBuffer str = new StringBuffer();
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    // Not sure what use the two characters are so I omit them
                    str.append((char) b);
                }
            }
            String[] data = str.toString().split("§");
            int onlinePlayers = Integer.parseInt(data[1]);
            System.out.print(onlinePlayers);
            server.setOnline(onlinePlayers);
        } catch (UnknownHostException e) {
            Lotus.getPlugin().getLogger().info(server.getName() + ": Unknown host. Make sure you typed the ip and port correctly.");
        } catch (IOException e) {
            Lotus.getPlugin().getLogger().info(server.getName() + ": connection refused. Make sure the server is online.");
        }
    }

}
