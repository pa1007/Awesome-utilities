package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.Warp;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import org.slf4j.Logger;
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
import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Optional;

public class TeleportCommand extends AbstractCommand {

    @Inject
    private Logger logger;

    private Path configPath;

    public TeleportCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"tpw"};
    }

    @Override
    public CommandSpec createCommand() {

        return CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("The warp's name"))))
                .permission(Permissions.TELEPORT_WARP)
                .description(Text.of("tp to a warp"))
                .executor(this)
                .build();
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
            src.sendMessage(
                    Text.of(TextColors.RED, "[warp] : ", TextColors.RESET, "The warp ", warpName, " was not found!")
            );
        }
        else {

            Optional<World> destWorld = Sponge.getGame().getServer().getWorld(matchingWarp.getWorldName());

            if (destWorld.isPresent()) {
                player.setLocation(new Location<>(destWorld.get(), matchingWarp.getPosition()));
            }
            else {
                src.sendMessage(Text.of(
                        TextColors.RED,
                        "[warp] : ",
                        TextColors.RESET,
                        "Error: the warp references a non-existing world!"
                ));
            }
        }

        return CommandResult.success();
    }
}

