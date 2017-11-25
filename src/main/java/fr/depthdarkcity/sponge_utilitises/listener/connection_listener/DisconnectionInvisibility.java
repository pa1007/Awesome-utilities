package fr.depthdarkcity.sponge_utilitises.listener.connection_listener;

import fr.depthdarkcity.sponge_utilitises.ChannelRegistry;
import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.listener.AbstractListener;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class DisconnectionInvisibility extends AbstractListener {

    public DisconnectionInvisibility(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getEventName() {
        return "DisconnectionInvisibility";
    }

    @Listener
    public void onClientDisconnect(ClientConnectionEvent.Disconnect e) {
        Player player = e.getCause().first(Player.class).get();

        if (player.hasPermission(Permissions.JOIN_WITH_INVISIBILITY)) {
            e.setChannel(ChannelRegistry.JOIN_SILENT);
        }
    }
}