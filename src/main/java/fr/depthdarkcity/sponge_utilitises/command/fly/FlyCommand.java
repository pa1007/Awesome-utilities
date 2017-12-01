package fr.depthdarkcity.sponge_utilitises.command.fly;

import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilitises.creator.CommonException;
import fr.depthdarkcity.sponge_utilitises.creator.Permissions;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FlyCommand extends AbstractCommand {

    private final Set<UUID> flying = new HashSet<>();

    public FlyCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"fly"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.optional(GenericArguments.player(Text.of("Optional player")))
                )
                .permission(Permissions.FLY_COMMAND)
                .description(Text.of("for flying"))
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
        boolean isFlying;
        if (args.getOne(Text.of("Optional player")).isPresent()) {
            Player player = args.<Player>getOne(Text.of("Optional player")).get();
            if (this.flying.contains(player.getUniqueId())) {
                this.flying.remove(player.getUniqueId());

                isFlying = false;
            }
            else {
                this.flying.add(player.getUniqueId());

                isFlying = true;

            }
            player.offer(Keys.CAN_FLY, isFlying);
        }
        else {
            Player player = (Player) src;
            if (this.flying.contains(player.getUniqueId())) {
                this.flying.remove(player.getUniqueId());

                isFlying = false;
            }
            else {
                this.flying.add(player.getUniqueId());

                isFlying = true;

            }
            player.offer(Keys.CAN_FLY, isFlying);
        }
        return CommandResult.success();

    }
}
