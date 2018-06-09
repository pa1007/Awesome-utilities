package fr.depthdarkcity.sponge_utilities.command.sword;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilities.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SwordGiveCommand extends AbstractCommand {

    public SwordGiveCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"sword"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments()
                .permission(Permissions.SWORD_COMMAND)
                .description(Text.of("Admin Command to give a OneShot Sword to an Admin "))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        ItemStack superMegaAwesomeSword = ItemStack.builder()
                .itemType(ItemTypes.DIAMOND_SWORD).build();
        EnchantmentData enchantmentData = superMegaAwesomeSword
                .getOrCreate(EnchantmentData.class).get();
        enchantmentData.set(enchantmentData.enchantments()
                                    .add(Enchantment.builder().type(EnchantmentTypes.SHARPNESS).level(1000).build()));
        superMegaAwesomeSword.offer(enchantmentData);
        superMegaAwesomeSword.offer(Keys.DISPLAY_NAME, Text.of(
                TextColors.BLUE, "The ",
                TextColors.GOLD, "Super ",
                TextColors.DARK_AQUA, "Mega ",
                TextColors.AQUA, "Admin Sword"
        ));
        superMegaAwesomeSword.offer(Keys.ATTACK_DAMAGE, 10000.0);
        superMegaAwesomeSword.offer(Keys.UNBREAKABLE, true);
        Player player = (Player) src;
        player.getInventory().set(superMegaAwesomeSword);
        src.sendMessage(Text.of("You have received The best item possible , ", superMegaAwesomeSword.toString()));
        return CommandResult.success();
    }


}
