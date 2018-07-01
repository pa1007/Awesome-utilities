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
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CreateCommand extends AbstractCommand {

    public CreateCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);

    }

    @Override
    public String[] getNames() {
        return new String[]{"Create"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("First Choice"))),
                        GenericArguments.string(Text.of("second Choice")),
                        GenericArguments.string(Text.of("Third Choice"))
                )
                .permission(Permissions.CREATE_VOTE_COMMAND)
                .description(Text.of("To create a vote"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        int    a     = 0;
        String vote1 = args.<String>getOne(Text.of("First Choice")).get();
        String vote2 = args.<String>getOne(Text.of("second Choice")).get();
        String vote3 = args.<String>getOne(Text.of("Third Choice")).get();
        if (!this.pluginInstance.getDeletable()) {
            this.pluginInstance.getChoices().put(vote1, a);
            this.pluginInstance.getChoices().put(vote2, a);
            this.pluginInstance.getChoices().put(vote3, a);
            Text main = Text.builder().color(TextColors.GOLD).append(Text.of(
                    "A new vote has been created by ",
                    src.getName()
            )).append(Text.NEW_LINE)
                    .append(Text.of("please choose between : ")).build();

            Text votetxt1 = Text.builder()
                    .append(Text.of(vote1)).onClick(
                            TextActions.runCommand("/vote choose " + vote1)).build();
            Text votetxt2 = Text.builder()
                    .append(Text.of(vote2)).onClick(
                            TextActions.runCommand("/vote choose " + vote2)).build();

            Text votetxt3 = Text.builder()
                    .append(Text.of(vote3)).onClick(
                            TextActions.runCommand("/vote choose " + vote3)).build();
            Sponge.getServer().getBroadcastChannel().send(main);
            Sponge.getServer().getBroadcastChannel().send(votetxt1);
            Sponge.getServer().getBroadcastChannel().send(votetxt2);
            Sponge.getServer().getBroadcastChannel().send(votetxt3);
            this.pluginInstance.setClosed(false);
            StringBuilder voteMessage =
                    new StringBuilder().append(main.toPlain()).append("\n").append(vote3).append("\n").append(
                            vote3).append("\n").append(vote3);
            pluginInstance.setVoteMessage(voteMessage.toString());
            return CommandResult.success();
        }
        else {
            throw new CommandException(Text.of(
                    "You can't create a vote if one is already running , please delete it before creating a new one ! "));
        }
    }

}
