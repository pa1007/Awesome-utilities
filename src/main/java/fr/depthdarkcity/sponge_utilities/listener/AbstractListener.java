package fr.depthdarkcity.sponge_utilities.listener;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import java.util.Objects;

public abstract class AbstractListener implements Listener {

    protected final SpongeUtilities pluginInstance;

    public AbstractListener(SpongeUtilities spongeUtilities) {
        Objects.requireNonNull(spongeUtilities);

        this.pluginInstance = spongeUtilities;
    }
}
