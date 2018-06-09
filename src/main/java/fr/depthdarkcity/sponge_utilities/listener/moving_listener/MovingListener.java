package fr.depthdarkcity.sponge_utilities.listener.moving_listener;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.listener.AbstractListener;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class MovingListener extends AbstractListener {

    public MovingListener(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getEventName() {
        return "Moving listener ";
    }

    @Listener
    public void onMoving(MoveEntityEvent e) {
        if (e.getTargetEntity() instanceof Player) {
            Player player = (Player) e.getTargetEntity();
            if (e.getSource().equals(player)) {
                if (pluginInstance.getFrozePlayer().contains(player.getUniqueId())) {
                    e.setCancelled(true);
                    player.sendMessage(Text.of(TextColors.RED, "You can't move while frozen !"));
                }

            }
        }
    }
}
