package fr.depthdarkcity.sponge_utilities.command.vote.subcommand;

import fr.depthdarkcity.sponge_utilities.creator.CommonException;
import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ChooseCommand extends AbstractCommand {

    public ChooseCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"choose"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("Your Vote"))))
                .permission(Permissions.CHOOSE_YOUR_RESLUT_COMMAND)
                .description(Text.of("For Voting"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player source = (Player) src;
        String vote   = args.<String>getOne(Text.of("Your Vote")).get();

        if (this.pluginInstance.getChoices().containsKey(vote) && !this.pluginInstance.getClosed()) {

            if (!this.pluginInstance.getVoter().contains(source.getUniqueId())) {

                this.pluginInstance.getChoices().computeIfPresent(vote, (key, val) -> val + 1);
                this.pluginInstance.getVoter().add(((Player) src).getUniqueId());

                source.sendMessage(Text.of(TextColors.GREEN, "Tou have successfully voted for ", vote));

                return CommandResult.success();
            }
            else {
                throw new CommandException(Text.of("You have already Voted please wait before voting "));
            }
        }
        else if (!this.pluginInstance.getClosed()) {
            throw new CommandException(Text.of("There is no vote yet"));
        }
        else if (!this.pluginInstance.getChoices().keySet().contains(vote)) {
            throw new CommandException(Text.of(
                    "There is no vote for this try one of this  ",
                    this.pluginInstance.getChoices().keySet()
            ));
        }

        return CommandResult.empty();
    }
}

