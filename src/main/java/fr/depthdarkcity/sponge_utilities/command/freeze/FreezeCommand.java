package fr.depthdarkcity.sponge_utilities.command.freeze;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;

public class FreezeCommand extends AbstractCommand {

    public FreezeCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"freeze"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.player(Text.of("Player")))
                .permission(Permissions.FREEZE_COMMAND)
                .description(Text.of("To freeze someone"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player source = null;
        Player player = args.<Player>getOne(Text.of("Player")).get();
        if (src instanceof Player) {
            source = (Player) src;
        }

        if (source != null) {
            if (player.getUniqueId().equals(source.getUniqueId())) {
                throw new CommandException(Text.of("You can't froze yourself"));
            }
            else {
                if (pluginInstance.getFrozePlayer().contains(player.getUniqueId())) {
                    src.sendMessage(Text.of(TextColors.GREEN, "unfroze player : ", player.getName()));
                    pluginInstance.getFrozePlayer().remove(player.getUniqueId());
                }
                else {
                    pluginInstance.getFrozePlayer().add(player.getUniqueId());
                    src.sendMessage(Text.of(TextColors.GOLD, "froze player : ", player.getName()));
                    player.sendTitle(Title.of(
                            Text.of(TextColors.RED, "You have been frozen"),
                            Text.of(TextColors.BLUE, "You have done something bad")
                    ));
                }
                return CommandResult.success();
            }
        }

        return CommandResult.success();
    }
}
