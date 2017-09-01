package fr.deprhdarkcity.utilitises.comand;

import fr.deprhdarkcity.utilitises.SpongeUtilities;
import java.util.Objects;

public abstract class AbstractCommand implements Command {

    protected final SpongeUtilities pluginInstance;

    public AbstractCommand(SpongeUtilities spongeUtilities) {
        Objects.requireNonNull(spongeUtilities);

        this.pluginInstance = spongeUtilities;
    }
}