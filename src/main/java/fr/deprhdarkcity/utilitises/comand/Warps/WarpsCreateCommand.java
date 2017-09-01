package fr.deprhdarkcity.utilitises.comand.Warps;

import com.flowpowered.math.vector.Vector3d;
import fr.deprhdarkcity.utilitises.Permissions;
import fr.deprhdarkcity.utilitises.SpongeUtilities;
import fr.deprhdarkcity.utilitises.Warp;
import fr.deprhdarkcity.utilitises.comand.AbstractCommand;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.storage.WorldProperties;

public class WarpsCreateCommand extends AbstractCommand {

    private Vector3d possition;
    private String   worldName;
    private String   playername;

    public WarpsCreateCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }


    @Override
    public String[] getNames() {
        return new String[]{"createwarp"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("Name of the warp"))))
                .permission(Permissions.CREATE_WARP)
                .description(Text.of("Create a warp"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[Warps] : ",
                    TextColors.RESET,
                    "You must be a player to perfom this command"
            ));
            return CommandResult.empty();
        }
        Player player = (Player) src;

        possition = player.getLocation().getPosition();
        WorldProperties worldName  = player.getWorld().getProperties();
        String          playername = player.getName();
        String          WarpName   = args.<String>getOne(Text.of("Name of the warp")).get();

        this.pluginInstance.getWarps().add(new Warp(possition, worldName.getWorldName(), WarpName, playername));
        pluginInstance.saveWarp();
        src.sendMessage(Text.of(
                TextColors.RED,
                "[Warps] : ",
                TextColors.RESET,
                "the warp ",
                WarpName,
                " has been set"
        ));
        return CommandResult.success();
    }

}
