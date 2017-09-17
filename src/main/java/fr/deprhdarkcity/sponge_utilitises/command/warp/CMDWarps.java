package fr.deprhdarkcity.sponge_utilitises.command.warp;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CMDWarps implements CommandExecutor {

    @Override
    public CommandResult execute(
            CommandSource src, CommandContext args
    ) throws CommandException {
        src.sendMessage(Text.of(TextColors.YELLOW, " /help"));
        return CommandResult.empty();
    }
}
