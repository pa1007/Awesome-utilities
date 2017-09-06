package fr.deprhdarkcity.sponge_utilitises.comand.ban;

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
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.BanTypes;

public class BanForBadComportementCommand extends AbstractCommand {

    public BanForBadComportementCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"BanForBadComportment"};
    }


    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .permission(Permissions.BAN_COMMAND)
                .description(Text.of("Ban player with a given reason"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Player     user    = args.<Player>getOne(Text.of("player")).get();
        Player     admin   = ((Player) src);
        BanService service = Sponge.getServiceManager().provide(BanService.class).get();
        Ban ban = Ban.builder().type(BanTypes.PROFILE).profile(user.getProfile())
                .reason(Text.of("Bad Comportment, plz go to The website to see the exact reason")).source(src).build();
        try {
            service.addBan(ban);
            if (service.hasBan(ban)){
                src.sendMessage(Text.of("The player ", user.getName(), " has been ban"));
                user.kick(Text.of("You have been banned for having a bad Comportment, plz go to the web site"));
                return CommandResult.success();
            }else {
                src.sendMessage(Text.of("The player ", user.getName(), " Has not been ban plz retry"));
                return CommandResult.empty();
            }
        }
        catch (Exception e) {
            src.sendMessage(Text.of(e));
            return CommandResult.empty();
        }

    }

}