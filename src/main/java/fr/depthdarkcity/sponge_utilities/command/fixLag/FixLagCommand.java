package fr.depthdarkcity.sponge_utilities.command.fixLag;

import fr.depthdarkcity.sponge_utilities.Permissions;
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
import org.spongepowered.api.world.Chunk;

public class FixLagCommand extends AbstractCommand {

    public FixLagCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"fixlight"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments()
                .permission(Permissions.FIX_COMMAND)
                .description(Text.of("Fix the light around the player"))
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
        player.getWorld().getLoadedChunks().forEach(Chunk::unloadChunk);
        player.sendMessage(Text.of("[Chunk] : The light as been fixed"));

        return CommandResult.success();
    }
}
