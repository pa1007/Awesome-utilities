package fr.depthdarkcity.sponge_utilitises.command.vanish;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishCommand extends AbstractCommand {

    private final Set<UUID> vanished = new HashSet<>();

    public VanishCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"vanish"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.VANISH_COMMAND)
                .description(Text.of("Set total invisibility for a player"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!(src instanceof Player)) {
            throw new CommandException(Text.of("You can't use the"));
        }

        Player  player = (Player) src;
        boolean isVanished;

        if (this.vanished.contains(player.getUniqueId())) {
            this.vanished.remove(player.getUniqueId());

            isVanished = false;
        }
        else {
            this.vanished.add(player.getUniqueId());

            isVanished = true;
        }

        player.offer(Keys.INVISIBLE, isVanished);
        player.offer(Keys.IS_SILENT, isVanished);
        player.offer(Keys.VANISH, isVanished);
        player.sendMessage(Text.of(isVanished ? "You have been vanished!" : "You have been unVanished!"));

        return CommandResult.success();
    }
}