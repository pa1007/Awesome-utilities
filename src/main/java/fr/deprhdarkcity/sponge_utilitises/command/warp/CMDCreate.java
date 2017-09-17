package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.Warp;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CMDCreate implements CommandExecutor {
    private final SpongeUtilities pluginInstance;

    CMDCreate(SpongeUtilities pluginInstance) {this.pluginInstance = pluginInstance;}

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String warpName = args.<String>getOne(Text.of("The warp's name")).orElseThrow(NullPointerException::new);
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[warp] : ",
                    TextColors.RESET,
                    "You must be a player to perform this command"
            ));
            return CommandResult.empty();
        }else if(!this.pluginInstance.getWarps().containsKey(warpName))
        {
            Player player = (Player) src;
            Warp warp = new Warp(
                    warpName,
                    player.getLocation().getPosition(),
                    player.getWorld().getName(),
                    player.getUniqueId()
            );

            pluginInstance.getWarps().put(warpName, warp);
            pluginInstance.saveWarps();

            src.sendMessage(Text.of(TextColors.RED, "[warp] : ", TextColors.RESET, "the warp ", warpName, " has been set"));
            return CommandResult.success();
        }else{
            src.sendMessages(Text.of("The warp with this name already exist, if you want to delete him ,plz do : "),
                             Text.builder("/delwarp <WarpName>").color(TextColors.RED).onClick(
                                     TextActions.runCommand("/warps del " + warpName)).build(), Text.of(TextColors.RESET, "or"),
                             Text.builder("/tpw <Warpname>").color(TextColors.RED).onClick(
                                     TextActions.runCommand("/warps tp " + warpName)).build());
            return CommandResult.empty();
        }
    }
}
