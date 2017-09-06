package fr.deprhdarkcity.sponge_utilitises.comand.Warps;

import com.flowpowered.math.vector.Vector3d;
import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.comand.AbstractCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class WarpsTPCommand extends AbstractCommand {

    @Inject
    private org.slf4j.Logger logger;

    private Path configPath;

    public WarpsTPCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"tpw"};
    }

    @Override
    public CommandSpec createCommand() {

        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("Name of the warp"))))
                .permission(Permissions.TELEPORT_WARP)
                .description(Text.of("tp to a warp"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        String fileName = args.<String>getOne(Text.of("Name of the warp")).get();
        Path   path     = pluginInstance.getWarpsDirectory();
        if (src instanceof Player) {

            try {
                String pathstring = path.toString();

                File    file    = new File(pathstring + "\\" + fileName + ".json");
                Scanner scanner = new Scanner(file);

                scanner.next(); // {
                scanner.next();// "position": TODO : Arrange
                scanner.next();// {
                scanner.next();// x

                String        xst = scanner.next(); // X
                StringBuilder sb  = new StringBuilder(xst);
                sb.deleteCharAt(xst.length() - 1);// removed the ","

                scanner.next();// y

                String        yst = scanner.next();//Y
                StringBuilder sb1 = new StringBuilder(yst);
                sb1.deleteCharAt(yst.length() - 1);// removed the ","

                scanner.next();//z

                String zst = scanner.next();// Z

                double x = Double.valueOf(sb.toString());
                double y = Double.valueOf(sb1.toString());
                double z = Double.valueOf(zst);

                Vector3d pos = new Vector3d().add(x, y, z);

                scanner.next();
                scanner.next();
                String Worldst = scanner.next();
                scanner.close();
                StringBuilder sb3 = new StringBuilder(Worldst);
                sb3.deleteCharAt(Worldst.length() - 1); // removed the ","
                sb3.deleteCharAt(sb3.length() - 1);
                sb3.deleteCharAt(0);
                String World = sb3.toString();


                Player player = (Player) src;
                try {
                    Vector3d                                    Before    = player.getLocation().getPosition();
                    Optional<org.spongepowered.api.world.World> warpWorld = Sponge.getServer().getWorld(World);
                    Location<org.spongepowered.api.world.World> warpTP =
                            new Location<org.spongepowered.api.world.World>(
                                    warpWorld.get(),
                                    pos.getX(),
                                    pos.getY(),
                                    pos.getZ()
                            );
                    player.setLocation(warpTP);
                    Vector3d After = player.getLocation().getPosition();
                    if (Before.equals(After)) {
                        if (Before.equals(pos)) {
                            player.sendMessage(Text.of(
                                    TextColors.RED,
                                    "[Warps] : ",
                                    TextColors.RESET,
                                    "You have not been teleported because you are already to this location"
                            ));
                            return CommandResult.success();
                        }
                        else {
                            src.sendMessages(
                                    Text.of(
                                            TextColors.RED,
                                            "[Warps] : ",
                                            TextColors.RESET,
                                            "You have not been teleported"
                                    ),
                                    Text.of(TextColors.RED, "Please contact a Moderator with you command")
                            );
                        }

                        return CommandResult.empty();
                    }
                    else {
                        player.sendMessage(Text.of(
                                TextColors.RED,
                                "[Warps] : ",
                                TextColors.RESET,
                                "You have been teleported to the Warp : ",
                                fileName
                        ));
                        return CommandResult.success();
                    }


                }
                catch (Exception e) {
                    logger.trace("[Error]" + String.valueOf(e));
                    src.sendMessage(Text.of(e));
                    return CommandResult.empty();
                }
            }
            catch (FileNotFoundException e) {
                // e.printStackTrace(); TODO : reactivate ?
                src.sendMessages(
                        Text.of(TextColors.RED, "[Warps] : ", "The sponge_utilitises is not set"),
                        Text.of(TextColors.RED, "Please retry with  ")
                ); //TODO ; Add the list of Warps
                return CommandResult.empty();
            }
        }
        else {
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[Warps] : ",
                    TextColors.DARK_RED,
                    "You must be a player to be able to teleport"
            ));
            return CommandResult.empty();
        }
    }
}

