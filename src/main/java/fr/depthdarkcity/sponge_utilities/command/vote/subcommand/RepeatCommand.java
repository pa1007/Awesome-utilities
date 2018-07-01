package fr.depthdarkcity.sponge_utilities.command.vote.subcommand;

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
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class RepeatCommand extends AbstractCommand {

    public RepeatCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"repeat"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.REPEAT_VOTE_COMMAND)
                .description(Text.of("To re-send a new vote"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!pluginInstance.getClosed()) {
            String[] choices = pluginInstance.getChoices().keySet().toArray(new String[]{});
            if (choices.length != 3) {
                throw new CommandException(Text.of("The vote is not created"));
            }
            else {
                Text main = Text.builder().color(TextColors.GOLD).append(Text.of(
                        "A new vote has been created by ",
                        src.getName()
                )).append(Text.NEW_LINE).append(Text.of("please choose between : ")).build();

                Text votetxt1 = Text.builder()
                        .append(Text.of(choices[0])).onClick(
                                TextActions.runCommand("/vote choose " + choices[0])).build();
                Text votetxt2 = Text.builder()
                        .append(Text.of(choices[1])).onClick(
                                TextActions.runCommand("/vote choose " + choices[1])).build();
                Text votetxt3 = Text.builder()
                        .append(Text.of(choices[2])).onClick(
                                TextActions.runCommand("/vote choose " + choices[2])).build();
                Sponge.getServer().getBroadcastChannel().send(main);
                Sponge.getServer().getBroadcastChannel().send(votetxt1);
                Sponge.getServer().getBroadcastChannel().send(votetxt2);
                Sponge.getServer().getBroadcastChannel().send(votetxt3);
                return CommandResult.success();
            }
        }
        else {
            throw new CommandException(Text.of("Nobody can vote , useless to send the vote "));
        }
    }
}
