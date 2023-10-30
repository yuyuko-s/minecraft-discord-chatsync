package org.komeiji.chatsync;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.logging.Logger;

public final class Chatsync extends JavaPlugin implements Listener {
    private Logger logger;
    public static String botId;
    public static TextChannel channel;
    @Override
    public void onEnable() {
        logger = Bukkit.getServer().getLogger();
        logger.info("ChatSync is now enabled.");
        saveDefaultConfig();
        String token = getConfig().getString("token");
        if (token == null || token.isEmpty()) {
            logger.severe("No bot token provided for Chatsync. Please enter the token in the config.");
            return;
        }

        JDA _jda = JDABuilder.createDefault(token)
                .enableIntents(
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new DiscordClient())
                .build();
        try {
            _jda.awaitReady();
        } catch (InterruptedException e) {
            logger.warning("JDA initialization was interrupted.");
        }
        String channelId = getConfig().getString("channel-id");
        if (channelId == null || channelId.isEmpty()) {
            logger.severe("Chatsync could not find channel id. Please check config.yml");
            throw new RuntimeException();
        }

        channel = _jda.getTextChannelById(channelId);

        if (channel == null) {
            logger.severe("ChatSync: Could not find a Discord channel with id ("+channelId+")provided in config.");
            throw new RuntimeException();
        }

        botId = _jda.getSelfUser().getId();


        channel.sendMessageEmbeds(basicEmbed(":white_check_mark: Server is now online.", Color.GREEN)).queue();

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        logger.info("ChatSync is now disabled.");
        channel.sendMessageEmbeds(basicEmbed(":octagonal_sign: Server is now offline.", Color.BLACK)).queue();
    }

    @EventHandler
    public void onPlayerChat(@NotNull AsyncPlayerChatEvent event) {
        channel.sendMessage(event.getPlayer().getName() + ": " + event.getMessage()).queue();
    }

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
        channel.sendMessageEmbeds(basicEmbed(":skull: " + event.getDeathMessage(), Color.BLACK)).queue();
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        channel.sendMessageEmbeds(basicEmbed(event.getPlayer().getName() + " has joined the server.", Color.GREEN)).queue();
    }

    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        channel.sendMessageEmbeds(basicEmbed(event.getPlayer().getName() + " has left the server.", Color.RED)).queue();
    }

    public static @NotNull MessageEmbed basicEmbed(String title, Color color) {
        return new EmbedBuilder()
                .setColor(color)
                .setTitle(title)
                .build();
    }
}


