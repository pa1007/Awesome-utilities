package fr.depthdarkcity.sponge_utilities.listener.chat_listener;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.listener.AbstractListener;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ChatListner extends AbstractListener {

    public ChatListner(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getEventName() {
        return "Chat listener";
    }

    @Listener
    public void onMessageSend(MessageEvent e) {
        if (e.getCause().first(Player.class).isPresent()) {
            Player player = e.getCause().first(Player.class).get();
            if (e.getSource().equals(player)) {
                if (pluginInstance.getFrozePlayer().contains(player.getUniqueId())) {
                    e.setMessageCancelled(true);
                    player.sendMessage(Text.of(TextColors.RED, "You cannot send a message while freeze"));
                }
            }
        }

    }


    @Listener
    public void onCommandSend(SendCommandEvent e) {
        if (e.getCause().first(Player.class).isPresent()) {
            Player player = e.getCause().first(Player.class).get();
            if (e.getSource().equals(player)) {
                if (pluginInstance.getFrozePlayer().contains(player.getUniqueId())) {
                    e.setCancelled(true);
                    player.sendMessage(Text.of(TextColors.RED, "You cannot send a command while freeze"));
                }
            }
        }
        if (e.getSource() instanceof CommandBlockSource) {
            if (!pluginInstance.isCommandBlockAllowed()) {
                pluginInstance.getLogger().debug("A command has been run by a command block while disable");
                e.setCancelled(true);
            }
        }
    }

}
