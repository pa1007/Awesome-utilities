package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ListCommand extends AbstractCommand {

    public ListCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"listwarp"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.LIST_WARP)
                .description(Text.of("list of warps"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("The warps are : ", TextColors.RED, pluginInstance.getWarps().keySet().toString()));
        return CommandResult.success();
    }
}
