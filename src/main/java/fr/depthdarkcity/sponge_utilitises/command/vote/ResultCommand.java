package fr.depthdarkcity.sponge_utilitises.command.vote;

import fr.depthdarkcity.sponge_utilitises.creator.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import java.util.Map;

public class ResultCommand extends AbstractCommand {

    public ResultCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"result"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.STOP_VOTE_COMMAND)
                .description(Text.of("Stop the result to move"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (this.pluginInstance.getClosed()) {
            Map<String, Integer> choices = pluginInstance.getChoices();

            String[] votes = choices.keySet().toArray(new String[0]);

            int vote1 = this.pluginInstance.getChoices().get(votes[0]);
            int vote2 = this.pluginInstance.getChoices().get(votes[1]);
            int vote3 = this.pluginInstance.getChoices().get(votes[2]);

            Text vote = Text.builder()
                    .append(Text.of(TextColors.GREEN,"There is the result of the vote : "))
                    .append(Text.NEW_LINE)
                    .append(Text.of(TextColors.GOLD,votes[0], " with ", vote1, " votes"))
                    .append(Text.NEW_LINE)
                    .append(Text.of(TextColors.GOLD,votes[1], " with ", vote2, " votes"))
                    .append(Text.NEW_LINE)
                    .append(Text.of(TextColors.GOLD,votes[2], " with ", vote3, " votes"))
                    .append(Text.NEW_LINE)
                    .append(Text.of(TextColors.GREEN,"Now you can Broadcast this Result before deleting them")).onClick(TextActions.runCommand(
                            "/vote broadcastResult")).build();
            src.sendMessage(vote);
            this.pluginInstance.setDeletable(true);
            return CommandResult.success();
        }
        else {
            throw new CommandException(Text.of("You have to stop the vote before getting the result"));
        }
    }
}
