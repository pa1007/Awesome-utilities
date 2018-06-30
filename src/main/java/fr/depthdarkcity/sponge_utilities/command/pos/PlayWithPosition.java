package fr.depthdarkcity.sponge_utilities.command.pos;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilities.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class PlayWithPosition extends AbstractCommand {

    public PlayWithPosition(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"playwithpos"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments()
                .permission(Permissions.PLAY_WITH_POS)
                .description(Text.of("Main command to play with pos created"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player player = (Player) src;

        player.sendMessage(Text.builder().append(Text.of("The location set are :"))
                                   .append(Text.NEW_LINE).append(Text.of("The first pos is : ")).append(Text.of((
                                                                                                                        pluginInstance.getFirstPos().containsKey(
                                                                                                                                player.getUniqueId())
                                                                                                                )
                                                                                                                        ? pluginInstance.getFirstPos().get(
                        player.getUniqueId()).toString()
                                                                                                                        : "null")).append(
                        Text.NEW_LINE).append(Text.of("The Second pos is : ")).append(Text.of((
                                                                                                      pluginInstance.getSecondPos().containsKey(
                                                                                                              player.getUniqueId())
                                                                                              )
                                                                                                      ? pluginInstance.getSecondPos().get(
                        player.getUniqueId()).toString()
                                                                                                      : "null")).build());
        return CommandResult.success();


    }
}
