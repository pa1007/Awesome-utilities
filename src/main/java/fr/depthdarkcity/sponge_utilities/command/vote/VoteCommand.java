package fr.depthdarkcity.sponge_utilities.command.vote;

import fr.depthdarkcity.sponge_utilities.creator.CommonException;
import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilities.command.Command;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class VoteCommand extends AbstractCommand {

    private final Command chooseCommand, createCommand, resultCommand, broadCastResultCommand, stopVote,
            delVote;


    public VoteCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
        this.chooseCommand = new ChooseCommand(spongeUtilities);
        this.createCommand = new CreateCommand(spongeUtilities);
        this.resultCommand = new ResultCommand(spongeUtilities);
        this.broadCastResultCommand = new BroadCastResultCommand(spongeUtilities);
        this.stopVote = new StopVoteCommand(spongeUtilities);
        this.delVote = new DeleteCommand(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"vote"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("Create , vote , get Result"))
                .executor(this)
                .child(this.createCommand.createCommand(), this.createCommand.getNames())
                .child(this.resultCommand.createCommand(), this.resultCommand.getNames())
                .child(this.chooseCommand.createCommand(), this.chooseCommand.getNames())
                .child(this.broadCastResultCommand.createCommand(), this.broadCastResultCommand.getNames())
                .child(this.delVote.createCommand(), this.delVote.getNames())
                .child(this.stopVote.createCommand(), this.stopVote.getNames())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src.hasPermission(Permissions.RESULT_COMMAND)
                 || src.hasPermission(Permissions.CREATE_VOTE_COMMAND)
                 || src.hasPermission(Permissions.BROADCAST_RESULT_COMMAND)
                 || src.hasPermission(Permissions.STOP_VOTE_COMMAND)
                 || src.hasPermission(Permissions.DELETE_VOTE_COMMAND)) {
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[vote] : ",
                    TextColors.RESET,
                    "Usage: /vote [choose,result,broadcastResult,stop,del]!"
            ));

        }
        else  if (src.hasPermission(Permissions.CHOOSE_YOUR_RESLUT_COMMAND)) {
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[vote] : ",
                    TextColors.RESET,
                    "Usage: /vote choose [1,2,3,4]!"
            ));
            return CommandResult.success();
        }
        else {
            throw new CommandException(CommonException.SOURCE_PERMISSION_EXCEPTION);
        }
        return CommandResult.empty();
    }


}
