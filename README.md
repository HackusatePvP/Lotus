# Lotus
Current version: 1.0.0
## Features
- Join Items (Server Selector, Player visibility, Settings, Cosmetics)
- Scoreboard ([AssembleScoreboard](https://github.com/ThatKawaiiSam/Assemble))
- Queues (With API)
- Very configurable
- Ranking system
- MySQL Database

## Dependencies
To make sure the plugin works correctly you must have a MySQL database. For the queues to work you will need to install [lotus-sync](https://github.com/HackusatePvP/Lotus-sync/releases) as a bungeecord plugin..

## Lang.yml
I have made a lang.yml for almost every message.

## Servers
You can add your servers to the server selector via the config. You can add as many as you want! There are some placeholders you can use with the lore. View [Placeholder.txt](https://github.com/HackusatePvP/Lotus/blob/master/Placeholder.txt)
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
    ip: "localhost" #Make sure this information is right. This will ping the server and get teh player count
    port: 25567
```
This plugin also comes with its own server pinger to get the amount of players that are online. The plugin refreshes the player count every minitue.

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
    name: "Rank1" #This is simply the name of the rank. It is recommended that this matches the name above.
    prefix: "&7[&fDefault&7]" #Prefix used in chat.
    ladder: 0 # This is the queue priority, the higher the better.
    permissions: #These are the permissions.
      - "this.is.a.permission"
```
To set a player to a certain rank you can execute the rank command. If you have buycraft or whatever its called now just make it execute the command /rank <player> <rank>. Be sure to spell the rank exactly how it is in the config. For exmaple for Starter rank do not type /rank player starter. The correct way would be /rank player Starter. The player rank's are stored in the database and the permissions are stored in the config.yml

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
I have made a QueueAPI class. With this class you can get a player's queue, queue position and if they are in a queue.
```java
Queue queue = QueueAPI.getQueueManager().getQueue("you can get a queue with a string");
Queue queue = QueueAPI.getQueueManager().getQueue(player); //you can get a queue with a player
QueuePlayer queuePlayer = QueueAPI.getQueueManager().getQueuePlayer(player); //This is a player object that stores the player's queue information such as position and priority
Queue queue = QueueAPI.getQueue(queuePlayer) //you can get a queue with a queuePlayer
int position = queuePlayer.getPosition(); //This is the position of the queue that the player is in
int size = QueueAPI.getQueueManager().getQueue(queuePlayer).getQueueSize(); //This is the total size of the Queue
```

