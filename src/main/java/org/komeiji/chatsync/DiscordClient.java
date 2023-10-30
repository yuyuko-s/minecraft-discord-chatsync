package org.komeiji.chatsync;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import javax.annotation.Nonnull;
import java.util.logging.Logger;
import static org.komeiji.chatsync.Chatsync.*;

public class DiscordClient extends ListenerAdapter {
    private final Logger logger = Bukkit.getServer().getLogger();
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event){
        try{
            if (event.getAuthor().getId().equals(botId)){
                return;
            }
            if (!event.getChannel().equals(channel)) {
                return;
            }
            String message = event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay();
            Bukkit.getServer().broadcast(Component.text(message));
        } catch (Exception e) {
            logger.severe("Chatsync: MessageReceivedEvent listener threw an exception:");
            throw new RuntimeException(e);
        }

    }
}