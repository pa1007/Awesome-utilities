package fr.deprhdarkcity.utilitises.comand.teleportation;

import fr.deprhdarkcity.utilitises.Permissions;
import fr.deprhdarkcity.utilitises.SpongeUtilities;
import fr.deprhdarkcity.utilitises.comand.AbstractCommand;
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

public class InterdimentionalTeleportationCommand extends AbstractCommand{

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
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .permission(Permissions.INTERDIMENTIONAL_TELEPORT)
                .description(Text.of("Create a warp"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player          source              = (Player) src;
            Player          destination         = args.<Player>getOne(Text.of("player")).get();
            Location<World> destinationLocation = destination.getLocation().copy();
                source.setLocation(destinationLocation);
                src.sendMessage(Text.of(TextColors.RED,"[TP] : ", TextColors.RESET , "You have been teleported to the player"));
            return CommandResult.success();
        }else {
            src.sendMessage(Text.of(TextColors.RED,"[TP] : ", TextColors.RESET , "You must be a player to be teleported " ));
            return CommandResult.empty();
        }

    }
}
