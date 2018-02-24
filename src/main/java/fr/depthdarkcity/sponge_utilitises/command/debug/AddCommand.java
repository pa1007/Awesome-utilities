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
        if (!SpongeUtilities.getDebugList().contains(player.getUniqueId())) {
            SpongeUtilities.getDebugList().add(player.getUniqueId());
            SpongeUtilities.debugInChatMessage(player, Text.of("You have been put in the Debug Chat"));
        }
        else {
            SpongeUtilities.debugInChatMessage(player, Text.of("You are already in the Debug Chat"));
        }
        return CommandResult.success();
    }
}
