package fr.depthdarkcity.sponge_utilities.command.commandblockblockcommand;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandBlockCommand extends AbstractCommand {

    public CommandBlockCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"commandblock"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments()
                .permission(Permissions.COMMAND_BLOCK_COMMAND)
                .description(Text.of("To block commandblock"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        pluginInstance.setCommandBlockAllowed(!pluginInstance.isCommandBlockAllowed());

        src.sendMessage(Text.of((pluginInstance.isCommandBlockAllowed())
                                        ? "The command Block are allowed"
                                        : "Command block are blocked"));
        return CommandResult.success();

    }
}
