package fr.depthdarkcity.sponge_utilities.command.structurgenerator;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;

public class StructureGenerator extends AbstractCommand {

    public StructureGenerator(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[0];
    }

    @Override
    public CommandSpec createCommand() {
        return null;
    }

    @Override
    public CommandResult execute(
            CommandSource src, CommandContext args
    ) throws CommandException {
        return null;
    }
}
