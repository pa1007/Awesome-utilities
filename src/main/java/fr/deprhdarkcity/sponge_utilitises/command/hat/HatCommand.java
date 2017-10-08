package fr.deprhdarkcity.sponge_utilitises.command.hat;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class HatCommand extends AbstractCommand {

    public HatCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"hat"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.HAT)
                .executor(this)
                .description(Text.of("To add a block in your head"))
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player pl = (Player) src;
        if (pl.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
            ItemStack stack = pl.getItemInHand(HandTypes.MAIN_HAND).get();
            ItemStack hand = stack.copy();
            hand.setQuantity(1);
            pl.setHelmet(hand);
                if (stack.getQuantity() > 1) {
                    stack.setQuantity(stack.getQuantity() - 1);
                    pl.setItemInHand(HandTypes.MAIN_HAND, stack);
                } else {
                    pl.setItemInHand(HandTypes.MAIN_HAND, null);
                }
                return CommandResult.success();
            }
        else {
            throw new CommandException(Text.of("You must have an item in your hand"));
        }


    }
}
