package fr.depthdarkcity.sponge_utilitises.listener.connection_listener;

import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.creator.ChannelRegistry;
import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.listener.AbstractListener;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ConnectionListener extends AbstractListener {

    public ConnectionListener(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getEventName() {
        return "Connection Listener";
    }

    @Listener
    public void onClientConnection(ClientConnectionEvent.Join e) {
        Player player = e.getCause().first(Player.class).get();
        if (player.hasPermission(Permissions.JOIN_WITH_INVISIBILITY)) {
            e.setChannel(ChannelRegistry.JOIN_SILENT);
            player.sendMessage(Text.of(
                    TextColors.BLUE,
                    "You have join the server without been seen,You can /vanish to not appear in tab "
            ));
        }
        if (player.hasPermission(Permissions.STAFF_CHAT)) {
            player.setMessageChannel(ChannelRegistry.STAFF_CHAT);
        }
    }

    @Listener
    public void onClientDisconnect(ClientConnectionEvent.Disconnect e) {
        Player player = e.getCause().first(Player.class).get();

        if (player.hasPermission(Permissions.JOIN_WITH_INVISIBILITY)) {
            e.setChannel(ChannelRegistry.JOIN_SILENT);
        }
    }
}
