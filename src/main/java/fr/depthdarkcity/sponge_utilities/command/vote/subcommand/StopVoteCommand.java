package fr.depthdarkcity.sponge_utilities.command.vote.subcommand;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class StopVoteCommand extends AbstractCommand {

    public StopVoteCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"stop"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("Reason"))))
                .permission(Permissions.STOP_VOTE_COMMAND)
                .description(Text.of("Stop the result to move"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (pluginInstance.getChoices().size() == 0) {
            throw new CommandException(Text.of("there is no vote"));
        }
        if (!this.pluginInstance.getClosed()) {
            this.pluginInstance.setClosed(true);
            Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(
                    TextColors.RED,
                    "[Vote] : ",
                    TextColors.RESET,
                    "The vote has been stopped by ",
                    src.getName(),
                    " with the given reason ",
                    args.<String>getOne(
                            Text.of("Reason")).get()
            ));
            return CommandResult.success();
        }
        else {
            src.sendMessage(Text.of("The vote has already been stop"));
            return CommandResult.empty();
        }

    }
}
