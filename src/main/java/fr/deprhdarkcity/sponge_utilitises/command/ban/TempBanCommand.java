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
import java.time.Instant;
import java.util.Objects;

public class TempBanCommand extends AbstractCommand {

    public TempBanCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"tempbanfor"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.enumValue(Text.of("reason"), Reason.class),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.longNum(Text.of("Time in second"))),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("optional reason")))
                )
                .permission(Permissions.TEMP_BAN_COMMAND)
                .description(Text.of("Temp Ban player with a given Reason"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player     user       = args.<Player>getOne(Text.of("player")).get();
        Long       time       = args.<Long>getOne(Text.of("Time in second")).get();
        Instant    now        = Instant.now();
        Instant    expiration = Instant.now().plusSeconds(time);

        BanService service    = Sponge.getServiceManager().provide(BanService.class).get();

        if (Objects.equals( args.<String>getOne(Text.of("reason")).get(), Reason.OTHER)) {
            if (!args.<String>getOne(Text.of("Other reason")).isPresent()) {
                src.sendMessage(Text.of(
                        TextColors.RED,
                        "You have chose the reason \"other\" you must leave the reason !"
                ));
                return CommandResult.empty();
            }
            else {
                String optional_reason = args.<String>getOne(Text.of("Optional reason")).get();
                Ban
                        ban =
                        Ban.builder().type(BanTypes.PROFILE).profile(user.getProfile()).expirationDate(expiration).startDate(
                                now)
                                .reason(Text.of(
                                        optional_reason,
                                        " , please go to The website to see the exact Reason"
                                )).source(src).build();
                try {
                    service.addBan(ban);
                    if (service.hasBan(ban)) {
                        src.sendMessage(Text.of("The player ", user.getName(), " has been ban for : ", time, " s"));
                        user.kick(Text.of(
                                "You have been banned Because ",
                                optional_reason,
                                " for ",
                                time,
                                " s , please go to the web site "
                        ));
                        return CommandResult.success();
                    }
                    else {
                        src.sendMessage(Text.of("The player ", user.getName(), " Has not been ban please retry"));
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
            Ban
                    ban =
                    Ban.builder().type(BanTypes.PROFILE).profile(user.getProfile()).expirationDate(expiration).startDate(
                            now)
                            .reason(Text.of(
                                    args.<String>getOne(Text.of("reason")).get(),
                                    " , please go to The web-site to see the exact Reason"
                            )).source(src).build();
            try {
                service.addBan(ban);
                if (service.hasBan(ban)) {
                    src.sendMessage(Text.of("The player ", user.getName(), " has been ban for : ", time, " s"));
                    user.kick(Text.of(
                            "You have been banned Because of ",
                            args.<String>getOne(Text.of("reason")).get(),
                            " for ",
                            time,
                            " s , please go to the web site "
                    ));
                    return CommandResult.success();
                }
                else {
                    src.sendMessage(Text.of("The player ", user.getName(), " Has not been ban please retry"));
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
