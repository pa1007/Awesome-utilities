package fr.depthdarkcity.sponge_utilities.command.killall;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilities.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class KillAllCommand extends AbstractCommand {

    public KillAllCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"killall"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments()
                .permission(Permissions.KILL_ALL_COMMAND)
                .description(Text.of("the command to kill all entity"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        int removedEntities = 0;
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player player = (Player) src;
        World  world  = player.getWorld();
        for (Entity entity : world.getEntities()) {
            if (pluginInstance.getListEntity().contains(entity.getType().getId())) {
                entity.remove();
                removedEntities++;
            }
        }
        src.sendMessage(Text.of("You have killed " + removedEntities + " Entity ! Murder"));
        return CommandResult.success();

    }
}
