package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CMDList implements CommandExecutor {
    protected final SpongeUtilities pluginInstance;

    public CMDList(SpongeUtilities pluginInstance) {this.pluginInstance = pluginInstance;}

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(pluginInstance.getWarps().keySet().isEmpty()){
            src.sendMessage(Text.of("There is no warp created"));
        }else {
            src.sendMessage(Text.of("The warps are : ", TextColors.RED, pluginInstance.getWarps().keySet().toString()));
        }

        return CommandResult.success();
    }
}
