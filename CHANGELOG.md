# Change-logs
## Update [1.0.2](https://github.com/HackusatePvP/Lotus/releases/tag/1.0.2)

### Additions
- You now now customise chat in the lang.yml
- Added Double Jump.
- Added Ender-Butts.
- Operators by default have permissions to bypass queue.
- Added border limit
- Added [SpawnCommand](LINK)
- Added a configurable spawn location
- Added queue message for players with lower priority ranks. 
### Fixes/Changes
- There will no longer be a queue cool-down if the server is not full, meaning the players will be sent to the server instantaneous.
- When a player joins the server, all players who have player visibility off will no longer see the new joined player.
- I have changed the QueueAPI completely please look at [README.md](https://github.com/HackusatePvP/Lotus) for updated documentation.
- Fixed null pointers while loading the player's profile.
- If a server went down while a player is in queue, it will remove the player from the queue instead of being stuck at position 0/0.
- Code cleanup

## Update 1.0.1
### Additions
- Added RankAPI see [README.md](https://github.com/HackusatePvP/Lotus) for full documentation. 
- Added ServerAPI see [README.md](https://github.com/HackusatePvP/Lotus) for full documentation. 
- Added Placeholders for player join message.
- Added [ItemsCommand](LINK) resets the players hotbar.
- Queues will no longer send players when the maxplayer limit is hit.
- Added tablist.
- Added tab.yml.
- Added Placeholders for tablist [Placeholder.txt](https://github.com/HackusatePvP/Lotus/blob/master/Placeholder.txt)
- Added player object to profile object.
### Fixes/Changes
- Added null pointers for every gui. (some may still exist)
- Fixed loading issue with config.yml
- Fixed issues loading player ranks. If rank is no longer exist then profile rank will be reset to default.
- Fixed cosmetic click events.
- Fixed saving issues with the config.yml

## Release 1.0
### Bugs
- Possible saving issues with lang.yml
- Sometimes queue will glitch out and set the first players to position 2. After testing it several times it seems that the person with the highest rank is sent first.
- Queues has not been tested on high player counts. The most players I've tested with it so far was 4.
- Cosmetics is still in development and is not completed nor is it tested.
- Before configuring the plugin make sure to read the [README.md](https://github.com/HackusatePvP/Lotus) it will guide you through the configuration.