package fr.depthdarkcity.sponge_utilities.listener.entity_listener;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.listener.AbstractListener;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.DamageEntityEvent;

public class DamageListener extends AbstractListener {

    public DamageListener(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getEventName() {
        return "Damage Listener";
    }

    @org.spongepowered.api.event.Listener
    public void onDammage(DamageEntityEvent e) {
        if (e.getTargetEntity() instanceof Player
            && pluginInstance.getGodded().contains(e.getTargetEntity().getUniqueId())) {
            e.setCancelled(true);
            e.getTargetEntity().offer(Keys.HEALTH, ((Player) e.getTargetEntity()).maxHealth().getMaxValue());
        }
    }
}
