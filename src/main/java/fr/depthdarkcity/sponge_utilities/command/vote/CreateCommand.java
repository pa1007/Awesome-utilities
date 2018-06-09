package fr.depthdarkcity.sponge_utilities.command.vote;

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
        int                  a           = 0;
        String               vote1       = args.<String>getOne(Text.of("First Choice")).get();
        String               vote2       = args.<String>getOne(Text.of("second Choice")).get();
        String               vote3       = args.<String>getOne(Text.of("Third Choice")).get();
        if (!this.pluginInstance.getDeletable()) {
            this.pluginInstance.getChoices() .put(vote1, a);
            this.pluginInstance.getChoices().put(vote2, a);
            Text.Builder vote = Text.builder()
                    .color(TextColors.GOLD)
                    .append(Text.of("A new vote has been created by ", src.getName()))
                    .append(Text.NEW_LINE)
                    .append(Text.of("please choose between"))
                    .append(Text.NEW_LINE)
                    .append(Text.of(vote1)).onClick(
                            TextActions.runCommand("/vote choose " + vote1))
                    .append(Text.NEW_LINE)
                    .append(Text.of(vote2)).onClick(
                            TextActions.runCommand("/vote choose " + vote2));
            vote.append(Text.NEW_LINE)
                    .append(Text.of(vote3)).onClick(
                    TextActions.runCommand("/vote choose " + vote3));
            this.pluginInstance.getChoices().put(vote3, a);
            Sponge.getServer().getBroadcastChannel().send(vote.build());
            this.pluginInstance.setClosed(false);
            return CommandResult.success();
        }
        else {
            throw new CommandException(Text.of(
                    "You can't create a vote if one is already running , please delete it before creating a new one ! "));
        }
    }

}
