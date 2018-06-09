package fr.depthdarkcity.sponge_utilities.command.vote;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BroadCastResultCommand extends AbstractCommand {


    public BroadCastResultCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"broadcastResult"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.BROADCAST_RESULT_COMMAND)
                .description(Text.of("Stop the result to move"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String[] votes = this.pluginInstance.getChoices().keySet().toArray(new String[0]);
        int      vote1 = this.pluginInstance.getChoices().get(votes[0]);
        int      vote2 = this.pluginInstance.getChoices().get(votes[1]);
        int      vote3 = this.pluginInstance.getChoices().get(votes[2]);
        Text vote = Text.builder().append(Text.of(TextColors.RED,"[Vote] : "))
                .append(Text.of(TextColors.GREEN,"There is the result of the vote : "))
                .append(Text.NEW_LINE)
                .append(Text.of(TextColors.GOLD,votes[0], " with ", vote1, " votes"))
                .append(Text.NEW_LINE)
                .append(Text.of(TextColors.GOLD,votes[1], " with ", vote2, " votes"))
                .append(Text.NEW_LINE)
                .append(Text.of(TextColors.GOLD,votes[2], " with ", vote3, " votes")).build();


        Sponge.getServer().getBroadcastChannel().send(vote);
        return CommandResult.empty();
    }
}
