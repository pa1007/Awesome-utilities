package fr.depthdarkcity.sponge_utilitises.command.warp;

import fr.depthdarkcity.sponge_utilitises.creator.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.Warp;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.Sponge;
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
import java.util.Optional;

public class TeleportCommand extends AbstractCommand {

    public TeleportCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[warp] : ",
                    TextColors.RESET,
                    "You must be a player to perform this command"
            ));
            return CommandResult.empty();
        }

        Player player       = (Player) src;
        String warpName     = args.<String>getOne(Text.of("The warp's name")).orElseThrow(NullPointerException::new);
        Warp   matchingWarp = this.pluginInstance.getWarps().get(warpName);

        if (matchingWarp == null) {
            src.sendMessages(
                    Text.of(
                            TextColors.RED,
                            "[warp] : ",
                            TextColors.RESET,
                            "The warp ",
                            warpName,
                            " was not found!, plz retry with : "
                    ),
                    Text.of(
                            TextColors.GOLD, pluginInstance.getWarps().keySet().toString()
                    )
            );
        }
        else {
            Optional<World> destWorld = Sponge.getGame().getServer().getWorld(matchingWarp.getWorldName());

            if (destWorld.isPresent()) {
                player.setLocation(new Location<>(destWorld.get(), matchingWarp.getPosition()));
                src.sendMessage(Text.of(
                        TextColors.RED,
                        "[warp] : ",
                        TextColors.RESET,
                        "You have been teleported to the warp " + warpName + " !"
                ));
            }
            else {
                src.sendMessage(Text.of(
                        TextColors.RED,
                        "[warp] : ",
                        TextColors.RESET,
                        "Error: the warp references a non-existing world ! "
                ));
            }
        }

        return CommandResult.success();
    }

    @Override
    public String[] getNames() {
        return new String[]{"teleport", "tp"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("teleport to a warp"))
                .permission(Permissions.TELEPORT_WARP)
                .executor(new TeleportCommand(pluginInstance))
                .arguments(GenericArguments.string(Text.of("The warp's name")))
                .build();
    }
}
