package fr.deprhdarkcity.sponge_utilitises.comand;

import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import java.util.Objects;

public abstract class AbstractCommand implements Command {

    protected final SpongeUtilities pluginInstance;

    public AbstractCommand(SpongeUtilities spongeUtilities) {
        Objects.requireNonNull(spongeUtilities);

        this.pluginInstance = spongeUtilities;
    }
}