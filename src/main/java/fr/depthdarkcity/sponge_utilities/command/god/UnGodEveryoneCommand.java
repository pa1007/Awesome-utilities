package fr.depthdarkcity.sponge_utilities.command.god;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class UnGodEveryoneCommand extends AbstractCommand {

    public UnGodEveryoneCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"ungodall"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("For giving the power of God to someone or you"))
                .permission(Permissions.UNGOD_COMMAND)
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (pluginInstance.getGodded().isEmpty()) {
            throw new CommandException(Text.of("There is nobody in god mode !"));
        }
        else {
            pluginInstance.getGodded().clear();
            src.sendMessage(Text.of("You have UnGod everyone"));
            return CommandResult.success();
        }
    }
}