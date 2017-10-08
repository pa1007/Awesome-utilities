package fr.deprhdarkcity.sponge_utilitises.command.vote;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
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
        Player source = ((Player) src);
        String vote   = args.<String>getOne(Text.of("Your Vote")).get();
        if (this.pluginInstance.getChoices().keySet().contains(vote) && !this.pluginInstance.getClosed()) {
            if (!this.pluginInstance.getVoter().contains(source.getUniqueId())) {
                Integer a = this.pluginInstance.getChoices().get(vote);
                a++;
                this.pluginInstance.getChoices().put(vote, a);
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

