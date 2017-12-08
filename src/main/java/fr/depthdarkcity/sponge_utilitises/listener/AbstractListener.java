package fr.depthdarkcity.sponge_utilitises.listener;

import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import java.util.Objects;

public abstract class AbstractListener implements Listener {

    protected final SpongeUtilities pluginInstance;

    public AbstractListener(SpongeUtilities spongeUtilities) {
        Objects.requireNonNull(spongeUtilities);

        this.pluginInstance = spongeUtilities;
    }
}
