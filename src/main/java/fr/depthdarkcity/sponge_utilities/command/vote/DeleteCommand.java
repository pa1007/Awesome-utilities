package fr.depthdarkcity.sponge_utilities.command.vote;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"del"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.DELETE_VOTE_COMMAND)
                .description(Text.of("Stop the result to move"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (this.pluginInstance.getDeletable()) {
            this.pluginInstance.deleteVote();
            src.sendMessage(Text.of(TextColors.GREEN,"Tou have successfully deleted the current vote"));
        }else {
            throw new CommandException(Text.of(TextStyles.BOLD,"You can't delete a vote if you don't see the result before !"));
        }
        return CommandResult.success();
    }
}
