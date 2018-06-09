package fr.depthdarkcity.sponge_utilities.listener.item_listener;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.listener.AbstractListener;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ItemListener extends AbstractListener {

    public ItemListener(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String getEventName() {
        return "Item Listeners";
    }

    @Listener
    public void onDropItem(DropItemEvent e) {
        if (e.getCause().first(Player.class).isPresent()) {
            Player player = e.getCause().first(Player.class).get();
            if (pluginInstance.getFrozePlayer().contains(player.getUniqueId())) {
                e.setCancelled(true);
                player.sendMessage(Text.of(TextColors.RED, "You can't drop an item while freeze !"));
            }


        }

    }
}
