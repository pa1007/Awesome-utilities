package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CMDDelete implements CommandExecutor {

    protected final SpongeUtilities pluginInstance;

    public CMDDelete(SpongeUtilities pluginInstance) {this.pluginInstance = pluginInstance;}

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String warpName = args.<String>getOne(Text.of("The warp's name")).orElseThrow(NullPointerException::new);

        if (this.pluginInstance.getWarps().remove(warpName) == null) {
            src.sendMessage(
                    Text.of(TextColors.RED, "[warp] : ", TextColors.RESET, "The warp ", warpName, " was not found!")
            );
        }
        else {
            src.sendMessage(
                    Text.of(TextColors.RED, "[warp] : ", TextColors.RESET, "The warp ", warpName, " has been deleted!")
            );

            this.pluginInstance.saveWarps();
        }

        return CommandResult.empty();
    }
}
