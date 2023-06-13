package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Managers.CustomItemsManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WeaponCommand extends CommandAPICommand {
    public WeaponCommand() {
        super("weapon");

        withArguments(new StringArgument("weapon").replaceSuggestions(ArgumentSuggestions.strings(CustomWeapon.weaponsNames)));

        executes((sender, args) -> {
            Player player = (Player) sender;
            String item = args.get(0).toString();

            if (CustomItemsManager.getCustomItemsMap().containsKey(item)) {
                ItemStack itemStack = CustomItemsManager.getCustomItemsMap().get(item);
                player.getInventory().addItem(itemStack);
            }
        }, ExecutorType.PLAYER)
                .register();
    }
}
