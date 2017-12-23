package fr.depthdarkcity.sponge_utilitises.command.debug;

import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilitises.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class LeaveCommand extends AbstractCommand {

    public LeaveCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"leave"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("Leave the debug team"))
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
            pluginInstance.getDebugList().remove(player.getUniqueId());
            pluginInstance.debugInChatMessage(player, Text.of("You have been removed from the debug chat"));
        }
        else {
            pluginInstance.debugInChatMessage(player, Text.of("You are not in the Debug Chat"));
        }
        return CommandResult.success();
    }
}
