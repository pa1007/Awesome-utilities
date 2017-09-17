package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class WarpCommand {
    private SpongeUtilities pluginInstance = new SpongeUtilities();


    private CommandSpec cmdCreate =
            CommandSpec.builder()
                    .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("The warp's name"))))
                    .permission(Permissions.CREATE_WARP)
                    .description(Text.of("Create a warp")).executor(new CMDCreate(this.pluginInstance)).build();

    private CommandSpec cmdteleport =
            CommandSpec.builder().description(Text.of("teleport to a warp")).executor(new CMDtp(pluginInstance)).permission(
                    Permissions.TELEPORT_WARP).arguments(GenericArguments.string(Text.of("The warp's name"))).build();

    private CommandSpec cmdDelete =
            CommandSpec.builder().description(Text.of("delete a warp")).executor(new CMDDelete(pluginInstance)).permission(
                    Permissions.DELETE_WARP).arguments(GenericArguments.string(Text.of("The warp's name"))).build();

    private CommandSpec cmdList =
            CommandSpec.builder().description(Text.of("list of warps")).executor(new CMDList(pluginInstance)).permission(
                    Permissions.LIST_WARP).build();
    public  CommandSpec cmdWarp = CommandSpec.builder()
            .description(Text.of("Base command"))
            .permission(Permissions.WARP)
            .child(cmdCreate, "create", "cr")
            .child(cmdteleport, "tp", "teleport")
            .child(cmdDelete, "del", "delete")
            .child(cmdList, "list", "li")
            .executor(new CMDWarps())
            .build();

    public WarpCommand(SpongeUtilities pluginInstance) {this.pluginInstance = pluginInstance;}
}
