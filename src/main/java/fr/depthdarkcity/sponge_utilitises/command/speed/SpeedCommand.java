package fr.depthdarkcity.sponge_utilitises.command.speed;

import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilitises.creator.CommonException;
import fr.depthdarkcity.sponge_utilitises.Permissions;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class SpeedCommand extends AbstractCommand {

    public SpeedCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"speed"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.longNum(Text.of("Speed")),
                        GenericArguments.optional(GenericArguments.player(Text.of("Optional player")))
                )
                .permission(Permissions.SPEED_COMMAND)
                .description(Text.of("adjusting your speed of walk/fly "))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player source = (Player) src;
        long   m      = args.<Long>getOne(Text.of("Speed")).get();

        if (m > 20 || m < 0) {
            throw new CommandException(Text.of("please add a number between 0 and 20"));
        }
        else if (!args.getOne(Text.of("Optional player")).isPresent()) {
            source.offer(Keys.WALKING_SPEED, 0.1 * m);
            source.offer(Keys.FLYING_SPEED, 0.05 * m);
            source.sendMessage(Text.of("You have set your speed to ", m));
            return CommandResult.success();
        }
        else {
            Player player = args.<Player>getOne(Text.of("Optional player")).get();
            player.offer(Keys.WALKING_SPEED, 0.1 * m);
            player.offer(Keys.FLYING_SPEED, 0.05 * m);
            src.sendMessage(Text.of("You have set the speed of the player: ", player.getName(), " to: ", m));
            player.sendMessage(Text.of(src.getName(), " have set your speed to: ", m));
            return CommandResult.success();
        }
    }
}
