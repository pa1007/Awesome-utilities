package fr.depthdarkcity.sponge_utilitises.command.god;

import fr.depthdarkcity.sponge_utilitises.creator.CommonException;
import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import java.util.UUID;

public class GodCommand extends AbstractCommand {

    public GodCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"god"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.optional(GenericArguments.player(Text.of("GOD"))))
                .description(Text.of("For giving the power of God to someone or you"))
                .permission(Permissions.GOD_COMMAND)
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player source = (Player) src;

        if (args.getOne(Text.of("GOD")).isPresent()) {
            UUID uuid = args.<Player>getOne(Text.of("GOD")).get().getUniqueId();
            if (pluginInstance.getGodded().contains(uuid)) {
                pluginInstance.getGodded().remove(uuid);
                source.sendMessage(Text.of(
                        "You have unGod the player : ",
                        args.<Player>getOne(Text.of("GOD")).get().getName()
                ));
                args.<Player>getOne(Text.of("GOD")).get().sendMessage(Text.of("You are no longer in God mode"));
            }
            else {
                pluginInstance.getGodded().add(uuid);
                src.sendMessage(Text.of(
                        "You have put the player : ",
                        args.<Player>getOne(Text.of("GOD")).get().getName()
                ));
                args.<Player>getOne(Text.of("GOD")).get().sendMessage(Text.of("You are in God mode"));
            }
        }
        else if (!pluginInstance.getGodded().contains(source.getUniqueId())) {
            pluginInstance.getGodded().add(source.getUniqueId());
            throw new CommandException(Text.of("You have been put in God Mode"));
        }
        else {
            pluginInstance.getGodded().remove(source.getUniqueId());
            throw new CommandException(Text.of("You have been unGodded"));
        }
        return CommandResult.success();
    }

}
