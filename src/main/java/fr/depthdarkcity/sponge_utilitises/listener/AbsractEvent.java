package fr.depthdarkcity.sponge_utilitises.listener;

import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import java.util.Objects;

public abstract class AbsractEvent implements Event {

    protected final SpongeUtilities pluginInstance;

    public AbsractEvent(SpongeUtilities spongeUtilities) {
        Objects.requireNonNull(spongeUtilities);

        this.pluginInstance = spongeUtilities;
    }
}
