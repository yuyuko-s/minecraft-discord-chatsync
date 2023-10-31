# minecraft-discord-chatsync
Bukkit plugin to synchronize Minecraft server chat with a Discord channel.

# What does it do?
Upon adding this plugin to your Minecraft server, and filling in the config.yml with a Discord bot token and specifying a channel, the plugin will deploy a Discord bot. This bot acts as a conduit for messages from the server to Discord, and from Discord to the server.
The bot also has special embedded messages for the following Minecraft events: death messages, player join/leave, server startup/shutdown.

# Setup
## Step 1: jar file & config.yml
In your server directory, locate the `plugins` folder. Paste chatsync-VERSION.jar in your plugins directory, and create a directory there named `chatsync`. From this repository download src/main/resources/config.yml, and put it in the `chatsync` directory. If you did this step improperly, the plugin will generate a config.yml on first load if it doesn't exist.

## Step 2: Discord Bot
Create your own Discord bot at the Discord developer portal. You can customize it however you want, as long as it has permission to read/send messages. Copy the bot's private token and paste it between the quotation marks in the specified field in config.yml.

## Step 3: Discord Channel
Enable developer mode in Discord settings, and right-click the channel where you want chat-syncing. Select copy channel ID, and paste it between the quotation marks in the specified field in config.yml.

## Step 4: Run
The plugin should be ready, restart your server if you haven't already.

# Issues
I made this primarily for myself. Only tested on a PaperMC server running Minecraft 1.20.2. Make an issue if it doesn't work on a specific version and I can fix it when I have time.
