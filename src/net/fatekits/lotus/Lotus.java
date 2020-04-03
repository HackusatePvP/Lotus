package net.fatekits.lotus;

import com.bizarrealex.azazel.Azazel;
import net.fatekits.lotus.board.BoardLink;
import net.fatekits.lotus.colors.ColorGUI;
import net.fatekits.lotus.colors.ColorListener;
import net.fatekits.lotus.commands.*;
import net.fatekits.lotus.configs.LangConfig;
import net.fatekits.lotus.configs.TabConfig;
import net.fatekits.lotus.cosmetics.CosmeticInventory;
import net.fatekits.lotus.cosmetics.CosmeticListener;
import net.fatekits.lotus.items.ItemListener;
import net.fatekits.lotus.items.ItemsInventory;
import net.fatekits.lotus.listeners.ChatEvent;
import net.fatekits.lotus.listeners.FixEvents;
import net.fatekits.lotus.listeners.PlayerListener;
import net.fatekits.lotus.profiles.ProfileListener;
import net.fatekits.lotus.profiles.ProfileManager;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.queue.QueueListener;
import net.fatekits.lotus.ranks.RankAPI;
import net.fatekits.lotus.ranks.RankManager;
import net.fatekits.lotus.servers.ServerAPI;
import net.fatekits.lotus.servers.ServerInventory;
import net.fatekits.lotus.servers.ServerManager;
import net.fatekits.lotus.servers.ServerTask;
import net.fatekits.lotus.settings.SettingsManager;
import net.fatekits.lotus.tab.TabLink;
import net.fatekits.lotus.utils.MySQL;
import net.fatekits.lotus.utils.PluginChannelListener;
import net.fatekits.lotus.utils.SaveTask;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

@Getter
public final class Lotus extends JavaPlugin {
    private static PluginChannelListener pcl;
    private final Logger log = Bukkit.getLogger();
    private MySQL mySQL;
    private static Lotus plugin;
    private ProfileManager profileManager;
    private ItemsInventory itemsInventory;
    private SaveTask saveTask;
    private LangConfig langConfig;
    private ServerManager serverManager;
    private ServerInventory serverInventory;
    private RankManager rankManager;
    private ColorGUI colorGUI;
    private SettingsManager settingsManager;
    private QueueAPI queueAPI;
    private ServerTask serverTask;
    private CosmeticInventory cosmeticInventory;
    private RankAPI rankAPI;
    private ServerAPI serverAPI;
    private TabConfig tabConfig;

    public void onEnable() {
        plugin = this;
        log.info("[Lotus] initiating...");
        log.info("[Lotus] loading config.yml ...");
        File file = new File(getDataFolder() + "config.yml");
        if (!file.exists()){
            saveDefaultConfig();
        }
        registerConfigs();
        log.info("[Lotus] pre-loading managers");
        registerManagers();
        log.info("[Lotus] connecting to database...");
        mySQL.profiles();
        log.info("[Lotus] loading all commands...");
        registerCommands();
        log.info("[Lotus] loading all listeners");
        registerListeners();
        log.info("[Lotus] pre-loading servers...");
        Lotus.getPlugin().getServerManager().loadServers();
        log.info("[Lotus] loading ServerAPI...");
        serverAPI = new ServerAPI();
        log.info("[Lotus] pre-loading all ranks");
        Lotus.getPlugin().getRankManager().loadRanks();
        log.severe("[Lotus] loading RankAPI...");
        rankAPI = new RankAPI();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // allow to send to BungeeCord
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "Return", pcl = new PluginChannelListener());
        log.info("[Lotus] loading scoreboard...");
        Assemble assemble = new Assemble(this, new BoardLink());
        assemble.setTicks(2);
        assemble.setAssembleStyle(AssembleStyle.MODERN);
        log.info("[Lotus] loading tablist...");
        new Azazel(this, new TabLink());
        log.info("[Lotus] All tasks completed.");
    }

    public void onDisable() {
        saveConfig();
        reloadConfig();
        mySQL.disconnectProfiles();
        plugin = null;
    }

    private void registerCommands() {
        getCommand("color").setExecutor(new ColorCommand());
        getCommand("console").setExecutor(new ConsoleCommand());
        getCommand("items").setExecutor(new ItemsCommand());
        getCommand("leavequeue").setExecutor(new LeaveQueueCommand());
        getCommand("lotus").setExecutor(new LotusCommand());
        getCommand("queue").setExecutor(new QueueCommand());
        getCommand("queues").setExecutor(new QueuesCommand());
        getCommand("rank").setExecutor(new RankCommand());
    }

    private void registerManagers() {
        mySQL = new MySQL(this);
        profileManager = new ProfileManager();
        saveTask = new SaveTask();
        saveTask.runTaskTimer(this, 0, 20);
        itemsInventory = new ItemsInventory();
        serverManager = new ServerManager();
        serverInventory = new ServerInventory();
        rankManager = new RankManager();
        colorGUI = new ColorGUI();
        settingsManager = new SettingsManager();
        queueAPI = new QueueAPI(this);
        serverTask = new ServerTask();
        serverTask.runTaskTimer(this, 0, 20);
        cosmeticInventory = new CosmeticInventory();
    }

    private void registerListeners() {
        Arrays.asList(new ProfileListener(), new ServerInventory(), new ItemListener(), new ColorListener(), new ChatEvent(), new SettingsManager(), new PlayerListener(), new FixEvents(), new QueueListener()
                , new CosmeticListener())
                .forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private void registerConfigs() {
        log.info("[Lotus] loading lang.yml ...");
        langConfig = new LangConfig(this, "lang.yml");
        langConfig.getConfig().options().copyDefaults(true);
        langConfig.saveConfig();
        langConfig.saveDefaultConfig();
        langConfig.reloadConfig();
        log.info("[Lotus] loading tab.yml ...");
        tabConfig = new TabConfig(this, "tab.yml");
        tabConfig.getConfig().options().copyDefaults(true);
        tabConfig.saveConfig();
        tabConfig.saveDefaultConfig();
        tabConfig.reloadConfig();
    }


    public static Lotus getPlugin() {
        return plugin;
    }
}
