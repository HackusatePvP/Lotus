# Lotus
Current version: 1.0.2
## Features
- Join Items (Server Selector, Player visibility, Settings, Cosmetics)
- Scoreboard ([AssembleScoreboard](https://github.com/ThatKawaiiSam/Assemble))
- Tablist ([AzazelTablist](https://github.com/bizarre/Azazel))
- Queues (With API)
- Very configurable
- Ranking system
- MySQL Database

## Dependencies
To make sure the plugin works correctly you must have a MySQL database. For the queues to work you will need to install [lotus-sync](https://github.com/HackusatePvP/Lotus-sync/releases) as a bungeecord plugin.

## Lang.yml
I have made a lang.yml for almost every message.

## Servers
You can add your servers to the server selector via the config. You can add as many as you want! There are some placeholders you can use with the lore. View [Placeholder.txt](LINK) This plugin also comes with its own server pinger to get the amount of players that are online. The plugin refreshes the player count every minute.
```yaml
servers:
  YourServer:
    material: "DIAMOND_SWORD"
    name: "&7* &9&lYour-Server"
    lore:
      - "&7&m----------------------"
      - "&7Online: %ONLINE%" #Simple Placeholder
      - "&7Queue: %QUEUESIZE%"
      - "&7&m----------------------"
    command: "queue YourServer"
    slot: 0 #Slot placement in the server gui
    ip: "localhost" #Make sure this information is right. This will ping the server and get the player count
    port: 25567
  YouServer2:
    material: "DIAMOND_SWORD"
    name: "&7* &9&lYour-Server"
    lore:
      - "&7&m----------------------"
      - "&7Simple Lore"
      - "&7&m----------------------"
    command: "queue YourServer2" #This must match whatever you put for queue name
    slot: 0 #Slot placement in the server gui
    ip: "localhost" #Make sure this information is right. This will ping the server and get then player count
    port: 25567
```

## Ranks
Lotus comes with a preset of ranks and permissions (The permissions are not hooked into the database). The ranks are required for the plugin to work but you do not have to use the rank's permissions. If you want to use pex instead it will work but thats only for permissions. You have to use this rank system for scoreboard and chat to work. The default rank you server has must be declared as "Default:" in the config. This is the default value the database sets for each player.
```yaml
  Rank2: #It is recommended that this matches the name below.
    name: "Rank2" #This is simply the name of the rank. It is recommended that this matches the name above.
    prefix: "&7[&4Rank2&7]" #Prefix used in chat.
    ladder: 2 # This is the queue priority, the higher the better.
    permissions: #These are the permissions.
      - "this.is.a.permission"
  Rank1: #It is recommended that this matches the name below.
    name: "Rank1" #This is simply the name of the rank. It is recommended that this matches the name above.
    prefix: "&7[&cRank1&7]" #Prefix used in chat.
    ladder: 1 # This is the queue priority, the higher the better.
    permissions: #These are the permissions.
      - "this.is.a.permission"
  Default: #THIS IS A REQUIRED RANK FOR THE PLUGIN TO LOAD
    name: "Default" #This is simply the name of the rank. It is recommended that this matches the name above.
    prefix: "&7[&fDefault&7]" #Prefix used in chat.
    ladder: 0 # This is the queue priority, the higher the better.
    permissions: #These are the permissions.
      - "this.is.a.permission"
```
To set a player to a certain rank you can execute the rank command. If you have buycraft or whatever its called now just make it execute the command /rank <player> <rank>. Be sure to spell the rank exactly how it is in the config. For exmaple for Starter rank do not type /rank player starter. The correct way would be /rank player Starter. The player rank's are stored in the database and the permissions are stored in the config.yml
Ranks comes with its own api called RankAPI

## Queues
Make sure you have [lotus-sync](https://github.com/HackusatePvP/Lotus-sync/releases) in your bungeecord plugins folder. Queues will send one player every 3 seconds. You can add as many queues as you want!
```yaml
queues:
  YourServer:
    name: "YourServer" #THIS HAS TO MATCH WHATEVER YOU PUT FOR SERVERS OR SERVER SELECTOR WILL NOT WORK
    maxplayers: 120
    paused: false #If this is true, the queue will stop sending players to the server and thus it will pause.
  YourServer2:
    name: "YourServer2" #This must match whatever you put for command in the servers section of the config.
    maxplayers: 120
    paused: false
```

## API's 
Lotus comes with several API's making additions to the plugin easy and simple. Each one will be explained below with examples. 

## QueueAPI
With the queue api you can get any information involving the queue and the players in the queue's. 
```java
Queue queue = QueueAPI.getQueue(player); //get the queue in which the player is in.
Queue queue = QueueAPI.getQueue("you can get a queue with a string");
QueuePlayer queuePlayer = QueueAPI.getQueuePlayer(player); //simple player object used for queues
Queue queue = QueueAPI.getQueue(queuePlayer); //you can get a queue by using an object.
int position = QueueAPI.getPositionPlayer(player);
```

## RankAPI
With the rank api you can get the rank object of a player. Which means you can get the permissions, prefixes, and name.
```java
Rank rank = RankAPI.getRank("you can get a rank with a string");
Rank rank = RankAPI.getRank(player); //you can get the rank of a player.
String name = rank.getName();
String prefix = rank.getPrefix();
List<String> permissions = rank.getPermissions();
```

## ServerAPI
With the server api you can get any information involving the servers, such as, players online, and status (coming soon).
```java
Server server = ServerAPI.getServer("you can get a server with a string"");
String name = server.getName();
int online = server.getOnline();
```
