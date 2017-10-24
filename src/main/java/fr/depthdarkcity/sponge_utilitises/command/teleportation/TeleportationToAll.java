package fr.depthdarkcity.sponge_utilitises.command.teleportation;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.Sponge;
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
import java.util.Collection;

public class TeleportationToAll extends AbstractCommand {

    public TeleportationToAll(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"tpall"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.optional(GenericArguments.player(Text.of("Player"))))
                .permission(Permissions.TELEPORT_ALL_TO_YOU)
                .description(Text.of("teleporting all the player to you"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Collection<Player> onlinePlayer = Sponge.getServer().getOnlinePlayers();
        Player             source       = (Player) src;
        Player[]        players        = onlinePlayer.stream().toArray(Player[]::new);
        Location<World> sourceLocation = source.getLocation();
        int             i              = 0;
        if (args.<Player>getOne(Text.of("Player")).isPresent()) {
            Player          player   = args.<Player>getOne(Text.of("Player")).get();
            Location<World> location = player.getLocation();
            do {
                players[i].setLocation(location);
                players[i].sendMessage(Text.of(
                        "You have been teleported to ",
                        player.getName()
                ));
                i++;
            }
            while (players.length > i);
            Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(
                    TextColors.RED,
                    "[Broadcast] : ",
                    TextColors.RESET, src.getName(), " has teleported everyone to : ", player.getName()
            ));
            return CommandResult.success();
        }
        else {
            do {
                players[i].setLocation(sourceLocation);
                players[i].sendMessage(Text.of("You have been teleported to ", source.getName()));
                i++;
            }
            while (players.length > i);
            Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(
                    TextColors.RED,
                    "[Broadcast] : ",
                    TextColors.RESET,
                    "everyone have been teleported to ",
                    src.getName()
            ));
            src.sendMessage(Text.of("All player has been teleported to you"));
            return CommandResult.success();
        }
    }
}
