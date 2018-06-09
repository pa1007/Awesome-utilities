package fr.depthdarkcity.sponge_utilities.command;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import java.util.Objects;

public abstract class AbstractCommand implements Command {

    protected final SpongeUtilities pluginInstance;

    public AbstractCommand(SpongeUtilities spongeUtilities) {
        Objects.requireNonNull(spongeUtilities);

        this.pluginInstance = spongeUtilities;
    }
}