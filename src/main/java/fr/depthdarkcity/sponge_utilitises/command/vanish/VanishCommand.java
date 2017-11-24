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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VanishCommand extends AbstractCommand {

    public Map<UUID, Boolean> vanished = new HashMap<>();

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
                .description(Text.of("Set total invisibility to a personne"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(
            CommandSource src, CommandContext args
    ) throws CommandException {
        Player player = (Player) src;
        if (!vanished.containsKey(player.getUniqueId()) || !vanished.get(player.getUniqueId())) {
            vanished.put(player.getUniqueId(), true);
            player.offer(Keys.INVISIBLE, true);
            player.offer(Keys.IS_SILENT, true);
            player.offer(Keys.VANISH, true);
            src.sendMessage(Text.of("You have been vanished"));
        }
        else {
            player.offer(Keys.INVISIBLE, false);
            player.offer(Keys.IS_SILENT, false);
            player.offer(Keys.VANISH, false);
            vanished.put(player.getUniqueId(), false);
            src.sendMessage(Text.of("You have been unVanished"));
        }
        return CommandResult.success();

    }
}
