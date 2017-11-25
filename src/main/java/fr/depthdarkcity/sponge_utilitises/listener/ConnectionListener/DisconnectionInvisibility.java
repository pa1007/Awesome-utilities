package fr.depthdarkcity.sponge_utilitises.listener.ConnectionListener;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.listener.AbsractEvent;
import fr.depthdarkcity.sponge_utilitises.ChannelRegister;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class DisconnectionInvisibility extends AbsractEvent {

    public DisconnectionInvisibility(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getName() {
        return "DisconnectionInvisibility";
    }

    @Listener
    public void onClientDisconnect(ClientConnectionEvent.Disconnect e) {
        Player player = e.getCause().first(Player.class).get();
        if (player.hasPermission(Permissions.JOIN_WITH_INVISIBILITY)) {
            e.setChannel(ChannelRegister.JOIN_SILENT);
        }
    }
}
