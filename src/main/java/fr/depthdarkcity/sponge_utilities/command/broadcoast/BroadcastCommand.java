package fr.depthdarkcity.sponge_utilities.command.broadcoast;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class BroadcastCommand extends AbstractCommand {

    public BroadcastCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"broadcast"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("message")))
                )
                .permission(Permissions.BROADCAST_COMMAND)
                .description(Text.of("To broadcast a message to the server"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String message = args.<String>getOne(Text.of("message")).orElseThrow(NullPointerException::new);
        SpongeUtilities.broadcast(Text.of(message));

        return CommandResult.success();
    }
}
