package fr.depthdarkcity.sponge_utilities.command.debug;

import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilities.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class AddCommand extends AbstractCommand {

    public AddCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"add"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("Enter the Chat"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(
            CommandSource src, CommandContext args
    ) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player player = (Player) src;
        if (!pluginInstance.getDebugList().contains(player.getUniqueId())) {
            pluginInstance.getDebugList().add(player.getUniqueId());
            pluginInstance.debugInChatMessage(player, Text.of("You have been put in the Debug Chat"));
        }
        else {
            pluginInstance.debugInChatMessage(player, Text.of("You are already in the Debug Chat"));
        }
        return CommandResult.success();
    }
}
