package fr.deprhdarkcity.sponge_utilitises.command.ban;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.BanTypes;
import java.util.Objects;

public class BanCommand extends AbstractCommand {

    public BanCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"Ban"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.enumValue(Text.of("Reason"), Reason.class),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("Player"))),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("Other Reason")))
                )
                .permission(Permissions.BAN_COMMAND)
                .description(Text.of("Ban player with a given Reason"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player     banned  = args.<Player>getOne(Text.of("Player")).get();
        Player     admin   = (Player) src;
        BanService service = Sponge.getServiceManager().provide(BanService.class).get();


        if (Objects.equals(args.<String>getOne(Text.of("Reason")).get(), Reason.OTHER)) {
            if (!args.<String>getOne(Text.of("Other Reason")).isPresent()) {
                src.sendMessage(Text.of(
                        TextColors.RED,
                        "You have chose the reason \"other\" you must leave the reason !"
                ));
                return CommandResult.empty();
            }
            else {
                String optional_reason = args.<String>getOne(Text.of("Other Reason")).get();

                Ban ban = Ban.builder().type(BanTypes.PROFILE).profile(banned.getProfile())
                        .reason(Text.of(optional_reason, " , please go to The website to see the exact Reason")).source(
                                src).build();
                try {
                    service.addBan(ban);
                    if (service.hasBan(ban)) {
                        src.sendMessage(Text.of("The player ", banned.getName(), " has been ban"));
                        banned.kick(Text.of("You have been banned for ",
                                            optional_reason,
                                            " , please go to the web-site"));
                        return CommandResult.success();
                    }
                    else {
                        src.sendMessage(Text.of("The player ", banned.getName(), " Has not been ban please retry"));
                        return CommandResult.empty();
                    }
                }
                catch (Exception e) {
                    src.sendMessage(Text.of(e));
                    return CommandResult.empty();
                }
            }
        }
        else {
            Ban ban = Ban.builder().type(BanTypes.PROFILE).profile(banned.getProfile())
                    .reason(Text.of(args.<String>getOne(Text.of("Reason")).get(), " , please go to The website to see the exact Reason")).source(src).build();
            try {
                service.addBan(ban);
                if (service.hasBan(ban)) {
                    src.sendMessage(Text.of("The player ", banned.getName(), " has been ban"));
                    banned.kick(Text.of("You have been banned for ", args.<String>getOne(Text.of("Reason")).get(), " , please go to the web-site"));
                    return CommandResult.success();
                }
                else {
                    src.sendMessage(Text.of("The player ", banned.getName(), " Has not been ban please retry"));
                    return CommandResult.empty();
                }
            }
            catch (Exception e) {
                src.sendMessage(Text.of(e));
                return CommandResult.empty();
            }
        }
    }
}