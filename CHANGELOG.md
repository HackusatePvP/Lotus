# Change-logs

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
- Sometimes queue will glitch out and set the first players to position 2. After several testing it seems that the person with the highest rank get sent first.
- Queues has not been tested on high player counts. The most players I've tested with it so far was 4.
- Cosmetics is still in development and is not completed nor is it tested.
- Before configuring the plugin make sure to read the [README.md](https://github.com/HackusatePvP/Lotus) it will guide you through the configuration.