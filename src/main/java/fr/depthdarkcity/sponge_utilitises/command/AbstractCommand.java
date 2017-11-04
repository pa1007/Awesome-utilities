package fr.depthdarkcity.sponge_utilitises.command;

import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import java.util.Objects;

public abstract class AbstractCommand implements Command {

    protected final SpongeUtilities pluginInstance;

    public AbstractCommand(SpongeUtilities spongeUtilities) {
        Objects.requireNonNull(spongeUtilities);

        this.pluginInstance = spongeUtilities;
    }
}