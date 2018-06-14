package fr.depthdarkcity.sponge_utilities.command.ban;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
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
import java.util.Optional;

public class BanCommand extends AbstractCommand {

    public BanCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"ban"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.enumValue(Text.of("reason"), Reason.class),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("reasonText")))
                )
                .permission(Permissions.BAN_COMMAND)
                .description(Text.of("Ban player with a given Reason"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player           user       = args.<Player>getOne("player").orElseThrow(NullPointerException::new);
        Reason           reason     = args.<Reason>getOne("reason").orElseThrow(NullPointerException::new);
        Optional<String> reasonText = args.getOne("reasonText");

        if (reason == Reason.OTHER && !reasonText.isPresent()) {

            src.sendMessage(Text.of(
                    TextColors.RED,
                    "Since you chose the \"other\" reason, the text must be provided!"
            ));

            return CommandResult.empty();
        }

        Text finalReason = reason == Reason.OTHER ? Text.of(reasonText.get()) : Text.of("Reason on the WebSite");

        Ban ban = Ban.builder()
                .type(BanTypes.PROFILE)
                .source(src)
                .profile(user.getProfile())
                .reason(reason == Reason.OTHER ? Text.of(reasonText.get()) : Text.of("Reason on the WebSite"))
                .build();

        Sponge.getServiceManager().provide(BanService.class).orElseThrow(NullPointerException::new).addBan(ban);

        user.kick(Text.join(Text.of("You are banned. Reason : \""), finalReason, Text.of("\" !")));

        return CommandResult.success();
    }
}