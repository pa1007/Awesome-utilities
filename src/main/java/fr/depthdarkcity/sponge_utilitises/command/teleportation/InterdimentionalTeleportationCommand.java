package fr.depthdarkcity.sponge_utilitises.command.teleportation;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilitises.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class InterdimentionalTeleportationCommand extends AbstractCommand {

    public InterdimentionalTeleportationCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"tpx"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.player(Text.of("player to you")))
                )
                .permission(Permissions.INTERDIMENTIONAL_TELEPORT)
                .description(Text.of("tp to a player"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player player = (Player) src;
        Player source = args.<Player>getOne(Text.of("player")).get();
        if (!args.getOne(Text.of("player to you")).isPresent()) {


            Location<World> destinationLocation = player.getLocation().copy();
            source.setLocation(destinationLocation);
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[TP] : ",
                    TextColors.RESET,
                    "You have been teleported to the player"
            ));
            source.sendMessage(Text.of(TextColors.RED,
                                       "[TP] : ", TextColors.RESET, "The player ", source.getName(),
                                       " has been teleported to you "
            ));
            return CommandResult.success();
        }
        else {
            Player destination = args.<Player>getOne(Text.of("player to you")).get();
            destination.setLocation(source.getLocation());
            destination.sendMessage(Text.of(
                    TextColors.RED,
                    "[TP] : ",
                    TextColors.RESET, "You have been teleported to ", source.getName()
            ));
            return CommandResult.success();
        }
    }

}
