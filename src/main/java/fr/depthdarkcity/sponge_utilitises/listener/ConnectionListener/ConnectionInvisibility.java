package fr.depthdarkcity.sponge_utilitises.listener.ConnectionListener;

import fr.depthdarkcity.sponge_utilitises.ChannelRegister;
import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.listener.AbsractEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ConnectionInvisibility extends AbsractEvent {

    public ConnectionInvisibility(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getName() {
        return "InvisibilityConnection";
    }

    @Listener
    public void onClientConnection(ClientConnectionEvent.Join e) {
        Player player = e.getCause().first(Player.class).get();
        if (player.hasPermission(Permissions.JOIN_WITH_INVISIBILITY)) {
            e.setChannel(ChannelRegister.JOIN_SILENT);
            player.sendMessage(Text.of(
                    TextColors.BLUE,
                    "You have join the server without been seen,You can /vanish to not appear in tab "
            ));
        }
        if (player.hasPermission(Permissions.STAFF_CHAT)) {
            player.setMessageChannel(ChannelRegister.STAFF_CHAT);
        }
    }
}
