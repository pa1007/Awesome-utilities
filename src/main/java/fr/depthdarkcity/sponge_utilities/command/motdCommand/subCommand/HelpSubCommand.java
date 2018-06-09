package fr.depthdarkcity.sponge_utilities.command.motdCommand.subCommand;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class HelpSubCommand extends AbstractCommand {

    public HelpSubCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"Help"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments()
                .permission(Permissions.MOTD_CHANGE)
                .description(Text.of("to get the help of the main and subCommand"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("\\u00A70 - BLACK\n"
                                + "\\u00A71 - DARK BLUE\n"
                                + "\\u00A72 - DARK GREEN\n"
                                + "\\u00A73 - DARK AQUA\n"
                                + "\\u00A74 - DARK RED\n"
                                + "\\u00A75 - DARK PURPLE\n"
                                + "\\u00A76 - GOLD\n"
                                + "\\u00A77 - GRAY\n"
                                + "\\u00A78 - DARK GRAY\n"
                                + "\\u00A79 - INDIGO\n"
                                + "\\u00A7a - GREEN\n"
                                + "\\u00A7b - AQUA\n"
                                + "\\u00A7c - RED\n"
                                + "\\u00A7d - PINK\n"
                                + "\\u00A7e - YELLOW\n"
                                + "\\u00A7f - WHITE\n"
                                + "\\u00A7k - Obfuscated\n"
                                + "\\u00A7l - Bold\n"
                                + "\\u00A7m - Strikethrough\n"
                                + "\\u00A7n - Underline\n"
                                + "\\u00A7o - Italic\n"
                                + "\\u00A7r - Reset \n"
                                + "\\n - New Line (1 possible)"));
        return CommandResult.success();
    }
}
