package fr.depthdarkcity.sponge_utilities.command.motdCommand.subCommand;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class AdminBroadcastCommand extends AbstractCommand {


    public AdminBroadcastCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"AdminBroadcast"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments()
                .permission(Permissions.MOTD_CHANGE)
                .description(Text.of("To broadCast the MOTD to the other Admin"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        throw new CommandException(Text.of("This is not supported in your sponge Version"));
    }
}
