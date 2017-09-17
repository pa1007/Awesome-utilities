package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.Warp;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import java.util.Optional;

public class CMDtp implements CommandExecutor {

    protected final SpongeUtilities pluginInstance;

    public CMDtp(SpongeUtilities pluginInstance) {
        this.pluginInstance = pluginInstance;}

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
}
